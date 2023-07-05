package com.hellguy39.hellnotes.feature.home.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.use_case.note.CopyNoteUseCase
import com.hellguy39.hellnotes.core.domain.use_case.note.DeleteNotesUseCase
import com.hellguy39.hellnotes.core.domain.use_case.note.PostProcessNoteUseCase
import com.hellguy39.hellnotes.core.domain.use_case.trash.MoveNotesToTrashUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.isNoteWrapperInvalid
import com.hellguy39.hellnotes.core.model.local.database.Checklist
import com.hellguy39.hellnotes.core.model.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.database.isChecklistValid
import com.hellguy39.hellnotes.core.model.toNoteWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    reminderRepository: ReminderRepository,
    labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val moveNotesToTrashUseCase: MoveNotesToTrashUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val postProcessNoteUseCase: PostProcessNoteUseCase,
    private val copyNoteUseCase: CopyNoteUseCase
) : ViewModel() {

    private val note: MutableStateFlow<Note> = MutableStateFlow(Note())
    private val checklists: MutableStateFlow<List<Checklist>> = MutableStateFlow(emptyList())
    private val reminds = reminderRepository.getAllRemindersStream()
    private val labels = labelRepository.getAllLabelsStream()

    private val noteWrapperState = combine(
        note, reminds, labels, checklists
    ) { note, reminders, labels, checklists ->
        when (note.id) {
            Note.EMPTY_ID -> NoteWrapperState.Empty
            else -> NoteWrapperState.Success(
                note.toNoteWrapper(
                    labels = labels, reminders = reminders, checklists = checklists
                )
            )
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteWrapperState.Empty
        )


    val uiState: StateFlow<NoteDetailUiState> = combine(
        noteWrapperState
    ) { flows ->
        NoteDetailUiState(
            noteWrapperState = flows[0]
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteDetailUiState()
        )

    fun send(uiEvent: NoteDetailUiEvent) {
        when (uiEvent) {

            is NoteDetailUiEvent.EditNoteTitle -> editNoteTitle(uiEvent.title)

            is NoteDetailUiEvent.EditNoteContent -> editNoteContent(uiEvent.text)

            is NoteDetailUiEvent.OpenNote -> openNote(uiEvent.noteId)

            is NoteDetailUiEvent.CloseNote -> close()

            is NoteDetailUiEvent.UpdateChecklistName ->
                updateChecklistName(uiEvent.checklist, uiEvent.name)

            is NoteDetailUiEvent.CheckAllChecklistItems ->
                checkAllChecklistItems(uiEvent.checklist, uiEvent.isCheck)

            is NoteDetailUiEvent.UpdateChecklistItem ->
                updateChecklistItem(uiEvent.checklist, uiEvent.oldItem, uiEvent.newItem)

            is NoteDetailUiEvent.UpdateIsArchived -> updateIsArchived(uiEvent.isArchived)

            is NoteDetailUiEvent.UpdateIsPinned -> updateIsPinned(uiEvent.isPinned)

            is NoteDetailUiEvent.AddChecklist -> addChecklist()

            is NoteDetailUiEvent.AddChecklistItem -> addChecklistItem(uiEvent.checklist)

            is NoteDetailUiEvent.DeleteChecklist -> deleteChecklist(uiEvent.checklist)

            is NoteDetailUiEvent.DeleteChecklistItem ->
                deleteChecklistItem(uiEvent.checklist, uiEvent.item)

            is NoteDetailUiEvent.DeleteNote -> moveNoteToTrash()

            is NoteDetailUiEvent.CopyNote -> copyNote(uiEvent.onCopied)

            is NoteDetailUiEvent.ExpandChecklist ->
                expandChecklist(uiEvent.checklist, uiEvent.isExpanded)
        }
    }

    private fun openNote(noteId: Long) {
        viewModelScope.launch {
            if (noteId == Note.EMPTY_ID) {
                note.update { Note(Note.EMPTY_ID) }
            } else {
                launch {
                    note.update { noteRepository.getNoteById(noteId) }
                }
                launch {
                    checklists.update { checklistRepository.getChecklistsByNoteId(noteId) }
                }
            }
        }
    }

    private fun copyNote(onCopied: (id: Long) -> Unit) {
        viewModelScope.launch {
            uiState.value.let { state ->
                if (state.noteWrapperState is NoteWrapperState.Success) {
                    val wrapper = state.noteWrapperState.noteWrapper
                    val noteId = copyNoteUseCase.invoke(wrapper)
                    onCopied(noteId)
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            uiState.value.let { state ->
                if (state.noteWrapperState is NoteWrapperState.Success) {
                    val postProcessedNote = prepareNoteForSave(state.noteWrapperState.noteWrapper)
                    noteRepository.updateNote(postProcessedNote.note)
                }
            }
        }
    }

    private fun close() {
        viewModelScope.launch {
            uiState.value.let { state ->
                if (state.noteWrapperState is NoteWrapperState.Success) {
                    val postProcessedNote = prepareNoteForSave(state.noteWrapperState.noteWrapper)
                    if (postProcessedNote.isNoteWrapperInvalid()) {
                        deleteNote()
                    } else {
                        noteRepository.updateNote(postProcessedNote.note)
                    }
                }
            }
        }
    }

    private suspend fun prepareNoteForSave(noteWrapper: NoteWrapper): NoteWrapper {
        var postProcessedNote = postProcessNoteUseCase.invoke(noteWrapper)
        val invalidChecklists = mutableListOf<Checklist>()

        for (i in postProcessedNote.checklists.indices) {
            val checklist = postProcessedNote.checklists[i]

            if (checklist.isChecklistValid()) {
                checklistRepository.updateChecklist(checklist)
            } else {
                invalidChecklists.add(checklist)
                checklist.id?.let { id -> checklistRepository.deleteChecklistById(id) }
            }
        }

        postProcessedNote = postProcessedNote.copy(
            checklists = postProcessedNote.checklists.toMutableList()
                .apply { removeAll(invalidChecklists) }
        )

        return postProcessedNote
    }

    private fun editNoteContent(text: String) {
        note.update { note -> note.copy(note = text, editedAt = System.currentTimeMillis()) }
        save()
    }

    private fun updateIsArchived(isArchived: Boolean) {
        note.update { note -> note.copy(isArchived = isArchived) }
        save()
    }

    private fun updateIsPinned(isPinned: Boolean) {
        note.update { note -> note.copy(isPinned = isPinned) }
        save()
    }

    private fun editNoteTitle(text: String) {
        note.update { note -> note.copy(title = text, editedAt = System.currentTimeMillis()) }
        save()
    }


    private fun moveNoteToTrash() {
        viewModelScope.launch {
            note.value.let { note -> moveNotesToTrashUseCase.invoke(listOf(note.toNoteWrapper())) }
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            note.value.let { note -> deleteNotesUseCase.invoke(listOf(note)) }
        }
    }

    private fun addChecklistItem(checklist: Checklist) {
        viewModelScope.launch {

            val items = checklist.items
                .toMutableList()
                .apply { add(ChecklistItem.newInstance()) }

            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)

                checklists
            }
        }
    }

    private fun deleteChecklist(checklist: Checklist) {
        viewModelScope.launch {
            checklists.update { checklists -> checklists.minus(checklist) }
            checklistRepository.deleteChecklistById(checklist.id ?: return@launch)
        }
    }

    private fun deleteChecklistItem(checklist: Checklist, item: ChecklistItem) {
        viewModelScope.launch {
            val items = checklist.items
                .toMutableList()
                .apply { remove(item) }

            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)

                checklists
            }
        }
    }

    private fun addChecklist() {
        viewModelScope.launch {
            val noteId = note.value.id ?: return@launch

            val checklist = Checklist.initialInstance(
                noteId = noteId,
                items = listOf(ChecklistItem.newInstance())
            )

            val checklistId = checklistRepository.insertChecklist(checklist = checklist)
            checklists.update { checklists ->
                checklists.plus(checklistRepository.getChecklistById(checklistId))
            }
        }
    }

    private fun updateChecklistName(checklist: Checklist, name: String) {
        viewModelScope.launch {
            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(name = name)

                checklists
            }
        }
    }

    private fun expandChecklist(checklist: Checklist, isExpanded: Boolean) {
        viewModelScope.launch {
            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(isExpanded = isExpanded)

                checklists
            }
        }
    }

    private fun updateChecklistItem(
        checklist: Checklist, oldItem: ChecklistItem, newItem: ChecklistItem
    ) {
        viewModelScope.launch {
            checklists.update { state ->
                val checklists = state.toMutableList()
                val items = checklist.items.toMutableList()

                items[items.indexOf(oldItem)] = newItem
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)

                checklists
            }
        }
    }

    private fun checkAllChecklistItems(checklist: Checklist, isCheck: Boolean) {
        viewModelScope.launch {

            val items = checklist.items
                .map { item -> item.copy(isChecked = isCheck) }

            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)

                checklists
            }
        }
    }
}

sealed class NoteDetailUiEvent {

    data class OpenNote(val noteId: Long) : NoteDetailUiEvent()

    object CloseNote : NoteDetailUiEvent()

    data class EditNoteTitle(val title: String) : NoteDetailUiEvent()

    data class EditNoteContent(val text: String) : NoteDetailUiEvent()

    data class UpdateIsPinned(val isPinned: Boolean) : NoteDetailUiEvent()

    data class UpdateIsArchived(val isArchived: Boolean) : NoteDetailUiEvent()

    data class CheckAllChecklistItems(
        val checklist: Checklist, val isCheck: Boolean
    ) : NoteDetailUiEvent()

    data class UpdateChecklistItem(
        val checklist: Checklist, val oldItem: ChecklistItem, val newItem: ChecklistItem
    ) : NoteDetailUiEvent()

    data class UpdateChecklistName(val checklist: Checklist, val name: String) : NoteDetailUiEvent()

    data class ExpandChecklist(val checklist: Checklist, val isExpanded: Boolean) :
        NoteDetailUiEvent()

    object AddChecklist : NoteDetailUiEvent()

    data class DeleteChecklist(val checklist: Checklist) : NoteDetailUiEvent()

    data class DeleteChecklistItem(val checklist: Checklist, val item: ChecklistItem) :
        NoteDetailUiEvent()

    data class AddChecklistItem(val checklist: Checklist) : NoteDetailUiEvent()

    object DeleteNote : NoteDetailUiEvent()

    data class CopyNote(val onCopied: (id: Long) -> Unit) : NoteDetailUiEvent()

}

data class NoteDetailUiState(
    val noteWrapperState: NoteWrapperState = NoteWrapperState.Empty
)

sealed class NoteWrapperState {

    data class Success(val noteWrapper: NoteWrapper) : NoteWrapperState()

    object Empty : NoteWrapperState()

}