package com.hellguy39.hellnotes.feature.notedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.CopyNoteUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.DeleteNoteUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.MoveNoteToTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.PostProcessNoteUseCase
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.database.addNewChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.isChecklistValid
import com.hellguy39.hellnotes.core.model.repository.local.database.removeChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.toggleAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        reminderRepository: ReminderRepository,
        labelRepository: LabelRepository,
        private val checklistRepository: ChecklistRepository,
        private val moveNoteToTrashUseCase: MoveNoteToTrashUseCase,
        private val deleteNoteUseCase: DeleteNoteUseCase,
        private val postProcessNoteUseCase: PostProcessNoteUseCase,
        private val copyNoteUseCase: CopyNoteUseCase,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val note: MutableStateFlow<Note> = MutableStateFlow(Note())
        private val checklists: MutableStateFlow<List<Checklist>> = MutableStateFlow(emptyList())

        val uiState: StateFlow<NoteDetailUiState> =
            combine(
                note,
                reminderRepository.getAllRemindersStream(),
                labelRepository.getAllLabelsStream(),
                checklists,
            ) { note, reminders, labels, checklists ->
                NoteDetailUiState(
                    wrapper =
                        note.toNoteDetailWrapper(
                            labels = labels,
                            reminders = reminders,
                            checklists = checklists,
                        ),
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = NoteDetailUiState(),
                )

        init {
            viewModelScope.launch {
                loadNote(getNoteId())
            }
        }

        fun send(uiEvent: NoteDetailUiEvent) {
            when (uiEvent) {
                is NoteDetailUiEvent.UpdateNoteTitle -> updateNoteTitle(uiEvent.title)

                is NoteDetailUiEvent.Close -> close()

                is NoteDetailUiEvent.UpdateChecklistName ->
                    updateChecklistName(uiEvent.checklist, uiEvent.name)

                is NoteDetailUiEvent.CheckAllChecklistItems ->
                    checkAllChecklistItems(uiEvent.checklist, uiEvent.isCheck)

                is NoteDetailUiEvent.UpdateChecklistItem ->
                    updateChecklistItem(uiEvent.checklist, uiEvent.oldItem, uiEvent.newItem)

                is NoteDetailUiEvent.ToggleIsArchived -> toggleIsArchived()

                is NoteDetailUiEvent.ToggleIsPinned -> toggleIsPinned()

                is NoteDetailUiEvent.UpdateNoteContent -> updateNoteContent(uiEvent.text)

                is NoteDetailUiEvent.AddChecklist -> addChecklist()

                is NoteDetailUiEvent.AddChecklistItem -> addChecklistItem(uiEvent.checklist)

                is NoteDetailUiEvent.DeleteChecklist -> deleteChecklist(uiEvent.checklist)

                is NoteDetailUiEvent.DeleteChecklistItem ->
                    deleteChecklistItem(uiEvent.checklist, uiEvent.item)

                is NoteDetailUiEvent.DeleteNote -> moveNoteToTrash()

                is NoteDetailUiEvent.CopyNote -> copyNote()

                is NoteDetailUiEvent.ExpandChecklist -> expandChecklist(uiEvent.checklist, uiEvent.isExpanded)
            }
        }

        private fun copyNote() {
            viewModelScope.launch {
                val wrapper = uiState.value.wrapper
                val noteId = copyNoteUseCase.invoke(wrapper)
                loadNote(noteId)
            }
        }

        private fun close() {
            viewModelScope.launch {
                val postProcessedNote = prepareNoteForSave()

                if (postProcessedNote.isNoteWrapperInvalid()) {
                    deleteNote()
                } else {
                    noteRepository.updateNote(postProcessedNote.note)
                }
            }
        }

        private suspend fun prepareNoteForSave(): NoteDetailWrapper {
            val wrapper = uiState.value.wrapper
            var postProcessedNote = postProcessNoteUseCase.invoke(wrapper)
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

            postProcessedNote =
                postProcessedNote.copy(
                    checklists =
                        postProcessedNote.checklists.toMutableList()
                            .apply { removeAll(invalidChecklists) },
                )

            return postProcessedNote
        }

        private fun updateNoteContent(text: String) {
            viewModelScope.launch {
                note.update { note ->
                    note.copy(
                        note = text,
                        editedAt = System.currentTimeMillis(),
                    )
                }
            }
        }

        private fun toggleIsArchived() {
            viewModelScope.launch {
                note.update { note -> note.copy(isArchived = note.isArchived.not()) }
            }
        }

        private fun toggleIsPinned() {
            viewModelScope.launch {
                note.update { note -> note.copy(isPinned = note.isPinned.not()) }
            }
        }

        private fun updateNoteTitle(text: String) {
            viewModelScope.launch {
                note.update { note -> note.copy(title = text, editedAt = System.currentTimeMillis()) }
            }
        }

        private fun moveNoteToTrash() {
            viewModelScope.launch {
                note.value.let { note -> moveNoteToTrashUseCase.invoke(note) }
            }
        }

        private fun deleteNote() {
            viewModelScope.launch {
                note.value.let { note -> deleteNoteUseCase.invoke(note) }
            }
        }

        private fun addChecklistItem(checklist: Checklist) {
            viewModelScope.launch {
                val items = checklist.items.addNewChecklistItem()

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

        private fun deleteChecklistItem(
            checklist: Checklist,
            item: ChecklistItem,
        ) {
            viewModelScope.launch {
                val items = checklist.items.removeChecklistItem(item)

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

                val checklist =
                    Checklist.initialInstance(
                        noteId = noteId,
                        items = listOf(ChecklistItem.newInstance()),
                    )

                val checklistId = checklistRepository.insertChecklist(checklist = checklist)
                checklists.update { checklists ->
                    checklists.plus(checklistRepository.getChecklistById(checklistId))
                }
            }
        }

        private fun updateChecklistName(
            checklist: Checklist,
            name: String,
        ) {
            viewModelScope.launch {
                checklists.update { state ->
                    val checklists = state.toMutableList()
                    checklists[checklists.indexOf(checklist)] = checklist.copy(name = name)
                    checklists
                }
            }
        }

        private fun expandChecklist(
            checklist: Checklist,
            isExpanded: Boolean,
        ) {
            viewModelScope.launch {
                checklists.update { state ->
                    val checklists = state.toMutableList()
                    checklists[checklists.indexOf(checklist)] = checklist.copy(isExpanded = isExpanded)
                    checklists
                }
            }
        }

        private fun updateChecklistItem(
            checklist: Checklist,
            oldItem: ChecklistItem,
            newItem: ChecklistItem,
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

        private fun checkAllChecklistItems(
            checklist: Checklist,
            isCheck: Boolean,
        ) {
            viewModelScope.launch {
                val items = checklist.items.toggleAll(isCheck)

                checklists.update { state ->
                    val checklists = state.toMutableList()
                    checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)
                    checklists
                }
            }
        }

        private fun loadNote(noteId: Long) {
            viewModelScope.launch {
                val note = noteRepository.getNoteById(noteId)
                val checklists = checklistRepository.getChecklistsByNoteId(noteId)

                this@NoteDetailViewModel.note.update { note }
                this@NoteDetailViewModel.checklists.update { checklists }
            }
        }

        private suspend fun getNoteId(): Long {
            return savedStateHandle.getArgument(Arguments.NoteId)
                .let { id ->
                    if (Arguments.NoteId.isEmpty(id)) {
                        noteRepository.insertNote(Note())
                    } else {
                        id
                    }
                }
        }
    }

sealed class NoteDetailUiEvent {
    data class UpdateNoteTitle(val title: String) : NoteDetailUiEvent()

    data class UpdateNoteContent(val text: String) : NoteDetailUiEvent()

    data object ToggleIsPinned : NoteDetailUiEvent()

    data object ToggleIsArchived : NoteDetailUiEvent()

    data class CheckAllChecklistItems(val checklist: Checklist, val isCheck: Boolean) : NoteDetailUiEvent()

    data class UpdateChecklistItem(val checklist: Checklist, val oldItem: ChecklistItem, val newItem: ChecklistItem) : NoteDetailUiEvent()

    data class UpdateChecklistName(val checklist: Checklist, val name: String) : NoteDetailUiEvent()

    data class ExpandChecklist(val checklist: Checklist, val isExpanded: Boolean) : NoteDetailUiEvent()

    data object AddChecklist : NoteDetailUiEvent()

    data class DeleteChecklist(val checklist: Checklist) : NoteDetailUiEvent()

    data class DeleteChecklistItem(val checklist: Checklist, val item: ChecklistItem) : NoteDetailUiEvent()

    data class AddChecklistItem(val checklist: Checklist) : NoteDetailUiEvent()

    data object DeleteNote : NoteDetailUiEvent()

    data object Close : NoteDetailUiEvent()

    data object CopyNote : NoteDetailUiEvent()
}

data class NoteDetailUiState(
    val wrapper: NoteDetailWrapper = NoteDetailWrapper.emptyInstance(),
)
