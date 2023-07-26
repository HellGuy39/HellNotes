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
import com.hellguy39.hellnotes.core.model.local.database.Reminder
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

    private val reminderEditDialogState = MutableStateFlow(ReminderEditDialogState())
    private val isDeleteAlertDialogOpen = MutableStateFlow(false)
    private val isMenuBottomSheetOpen = MutableStateFlow(false)
    private val isAttachmentBottomSheetOpen = MutableStateFlow(false)

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

    private val noteEditDialogState = combine(
        reminderEditDialogState,
        isMenuBottomSheetOpen,
        isAttachmentBottomSheetOpen,
        isDeleteAlertDialogOpen,
    ) { reminderEditDialogState, isMenuBottomSheetOpen, isAttachmentBottomSheetOpen, isDeleteAlertDialogOpen ->
        NoteEditDialogState(
            reminderEditDialogState = reminderEditDialogState,
            isMenuBottomSheetOpen = isMenuBottomSheetOpen,
            isAttachmentBottomSheetOpen = isAttachmentBottomSheetOpen,
            isDeleteAlertDialogOpen = isDeleteAlertDialogOpen,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteEditDialogState()
        )

    val uiState: StateFlow<NoteDetailUiState> = combine(
        noteWrapperState,
        noteEditDialogState
    ) { noteWrapperState, noteEditDialogState ->
        NoteDetailUiState(
            noteWrapperState = noteWrapperState,
            noteEditDialogState = noteEditDialogState
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteDetailUiState()
        )

    fun send(uiEvent: NoteEditUiEvent) {
        when (uiEvent) {

            is NoteEditUiEvent.EditNoteTitle -> editNoteTitle(uiEvent.title)

            is NoteEditUiEvent.EditNoteContent -> editNoteContent(uiEvent.text)

            is NoteEditUiEvent.OpenNote -> openNote(uiEvent.noteId)

            is NoteEditUiEvent.CloseNote -> close()

            is NoteEditUiEvent.UpdateChecklistName ->
                updateChecklistName(uiEvent.checklist, uiEvent.name)

            is NoteEditUiEvent.CheckAllChecklistItems ->
                checkAllChecklistItems(uiEvent.checklist, uiEvent.isCheck)

            is NoteEditUiEvent.UpdateChecklistItem ->
                updateChecklistItem(uiEvent.checklist, uiEvent.oldItem, uiEvent.newItem)

            is NoteEditUiEvent.UpdateIsArchived -> updateIsArchived()

            is NoteEditUiEvent.UpdateIsPinned -> updateIsPinned()

            is NoteEditUiEvent.AddChecklist -> addChecklist()

            is NoteEditUiEvent.AddChecklistItem -> addChecklistItem(uiEvent.checklist)

            is NoteEditUiEvent.DeleteChecklist -> deleteChecklist(uiEvent.checklist)

            is NoteEditUiEvent.DeleteChecklistItem ->
                deleteChecklistItem(uiEvent.checklist, uiEvent.item)

            is NoteEditUiEvent.DeleteNote -> moveNoteToTrash()

            is NoteEditUiEvent.CopyNote -> copyNote(uiEvent.onCopied)

            is NoteEditUiEvent.ExpandChecklist ->
                expandChecklist(uiEvent.checklist, uiEvent.isExpanded)

            is NoteEditUiEvent.OpenMenuBottomSheet -> openMenuBottomSheet(uiEvent.isOpen)

            is NoteEditUiEvent.OpenAttachmentBottomSheet -> openAttachmentBottomSheet(uiEvent.isOpen)

            is NoteEditUiEvent.OpenDeleteAlertDialog -> openDeleteAlertDialog(uiEvent.isOpen)

            is NoteEditUiEvent.MakeACopy -> makeACopy()

            is NoteEditUiEvent.Share -> share()

            is NoteEditUiEvent.OpenColorDialog -> openColorDialog(uiEvent.isOpen)

            is NoteEditUiEvent.AddLabel -> addLabel()
        }
    }

    private fun addLabel() {

    }

    private fun makeACopy() {

    }

    private fun share() {

    }

    private fun openColorDialog(isOpen: Boolean) {

    }

    private fun openDeleteAlertDialog(isOpen: Boolean) {
        viewModelScope.launch {
            isDeleteAlertDialogOpen.update { isOpen }
        }
    }

    private fun openMenuBottomSheet(isOpen: Boolean) {
        viewModelScope.launch {
            isMenuBottomSheetOpen.update { isOpen }
        }
    }

    private fun openAttachmentBottomSheet(isOpen: Boolean) {
        viewModelScope.launch {
            isAttachmentBottomSheetOpen.update { isOpen }
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

    private fun updateIsArchived() {
        val isArchived = !note.value.isArchived
        note.update { note -> note.copy(isArchived = isArchived) }
        save()
    }

    private fun updateIsPinned() {
        val isPinned = !note.value.isPinned
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

sealed class NoteEditUiEvent {

    data class OpenNote(val noteId: Long) : NoteEditUiEvent()

    object CloseNote : NoteEditUiEvent()

    data class EditNoteTitle(val title: String) : NoteEditUiEvent()

    data class EditNoteContent(val text: String) : NoteEditUiEvent()

    object UpdateIsPinned: NoteEditUiEvent()

    object UpdateIsArchived: NoteEditUiEvent()

    data class CheckAllChecklistItems(
        val checklist: Checklist, val isCheck: Boolean
    ) : NoteEditUiEvent()

    data class UpdateChecklistItem(
        val checklist: Checklist, val oldItem: ChecklistItem, val newItem: ChecklistItem
    ) : NoteEditUiEvent()

    data class UpdateChecklistName(val checklist: Checklist, val name: String) : NoteEditUiEvent()

    data class ExpandChecklist(val checklist: Checklist, val isExpanded: Boolean) :
        NoteEditUiEvent()

    object AddChecklist : NoteEditUiEvent()

    data class DeleteChecklist(val checklist: Checklist) : NoteEditUiEvent()

    data class DeleteChecklistItem(val checklist: Checklist, val item: ChecklistItem) :
        NoteEditUiEvent()

    data class AddChecklistItem(val checklist: Checklist) : NoteEditUiEvent()

    object DeleteNote : NoteEditUiEvent()

    data class CopyNote(val onCopied: (id: Long) -> Unit) : NoteEditUiEvent()

    data class OpenMenuBottomSheet(val isOpen: Boolean) : NoteEditUiEvent()

    data class OpenAttachmentBottomSheet(val isOpen: Boolean) : NoteEditUiEvent()

    data class OpenDeleteAlertDialog(val isOpen: Boolean) : NoteEditUiEvent()

    data class OpenColorDialog(val isOpen: Boolean) : NoteEditUiEvent()

    object AddLabel : NoteEditUiEvent()

    object Share : NoteEditUiEvent()

    object MakeACopy : NoteEditUiEvent()

}

data class NoteDetailUiState(
    val noteWrapperState: NoteWrapperState = NoteWrapperState.Empty,
    val noteEditDialogState: NoteEditDialogState = NoteEditDialogState()
)

data class NoteEditDialogState(
    val reminderEditDialogState: ReminderEditDialogState = ReminderEditDialogState(),
    val isMenuBottomSheetOpen: Boolean = false,
    val isAttachmentBottomSheetOpen: Boolean = false,
    val isDeleteAlertDialogOpen: Boolean = false
)

data class ReminderEditDialogState(
    val isOpen: Boolean = false,
    val reminder: Reminder? = null
)

sealed class NoteWrapperState {

    data class Success(val noteWrapper: NoteWrapper) : NoteWrapperState()

    object Empty : NoteWrapperState()

}