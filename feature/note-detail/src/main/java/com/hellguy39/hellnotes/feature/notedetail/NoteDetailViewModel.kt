/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.notedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.domain.repository.notes.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.ReminderRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.CopyNoteUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.MoveNoteToTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.PostProcessNoteUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.RestoreNoteFromTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.trash.DeleteNoteUseCase
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import com.hellguy39.hellnotes.core.model.repository.local.database.addNewChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.isChecklistValid
import com.hellguy39.hellnotes.core.model.repository.local.database.removeChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.toggleAll
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarAction
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarController
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarEvent
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        reminderRepository: ReminderRepository,
        labelRepository: LabelRepository,
        private val restoreNoteFromTrashUseCase: RestoreNoteFromTrashUseCase,
        private val checklistRepository: ChecklistRepository,
        private val moveNoteToTrashUseCase: MoveNoteToTrashUseCase,
        private val deleteNoteUseCase: DeleteNoteUseCase,
        private val postProcessNoteUseCase: PostProcessNoteUseCase,
        private val copyNoteUseCase: CopyNoteUseCase,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

        private val viewModelState = MutableStateFlow(NoteDetailViewModelState())

        private val note: MutableStateFlow<Note> = MutableStateFlow(Note())
        private val checklists: MutableStateFlow<List<Checklist>> = MutableStateFlow(emptyList())

        private val _navigationEvents = Channel<NoteDetailNavigationEvent>()
        val navigationEvents = _navigationEvents.receiveAsFlow()

        val uiState: StateFlow<NoteDetailUiState> =
            combine(
                note,
                reminderRepository.getAllRemindersStream(),
                labelRepository.getAllLabelsStream(),
                checklists,
                viewModelState
            ) { note, reminders, labels, checklists, viewModelState ->
                NoteDetailUiState(
                    wrapper =
                        note.toNoteWrapper(
                            labels = labels,
                            reminders = reminders,
                            checklists = checklists,
                        ),
                    isReadOnly = note.atTrash,
                    isAttachmentOpen = viewModelState.isAttachmentOpen,
                    isMenuOpen = viewModelState.isMenuOpen
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
            viewModelScope.launch {
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

                    is NoteDetailUiEvent.MoveToTrash -> close(moveToTrash = true)

                    is NoteDetailUiEvent.CopyNote -> copyNote()

                    is NoteDetailUiEvent.ExpandChecklist -> expandChecklist(uiEvent.checklist, uiEvent.isExpanded)

                    is NoteDetailUiEvent.DeleteForever -> deleteForever()

                    is NoteDetailUiEvent.Restore -> restore()

                    is NoteDetailUiEvent.Redo -> {}

                    is NoteDetailUiEvent.Undo -> {}

                    is NoteDetailUiEvent.ShowAttachment -> showAttachment(uiEvent.isOpen)

                    is NoteDetailUiEvent.ShowMenu -> showMenu(uiEvent.isOpen)

                    is NoteDetailUiEvent.CreateReminder -> createReminder()

                    is NoteDetailUiEvent.LabelClick -> labelClick(uiEvent.label)

                    is NoteDetailUiEvent.ReminderClick -> reminderClick(uiEvent.reminder)
                }
            }
        }

        private suspend fun createReminder() {
            if (!uiState.value.isReadOnly) {
                val noteId = uiState.value.wrapper.note.id ?: 0
                val reminderId = Arguments.ReminderId.emptyValue
                _navigationEvents.send(NoteDetailNavigationEvent.NavigateToReminderEdit(noteId, reminderId))
            }
        }

        private suspend fun labelClick(label: Label) {
            if (!uiState.value.isReadOnly) {
                val noteId = uiState.value.wrapper.note.id ?: 0
                _navigationEvents.send(NoteDetailNavigationEvent.NavigateLabelSelection(noteId))
            }
        }

        private suspend fun reminderClick(reminder: Reminder) {
            if (!uiState.value.isReadOnly) {
                val noteId = uiState.value.wrapper.note.id ?: 0
                val reminderId = reminder.id ?: 0
                _navigationEvents.send(NoteDetailNavigationEvent.NavigateToReminderEdit(noteId, reminderId))
            }
        }

        private suspend fun showMenu(isOpen: Boolean) {
            viewModelState.update { state -> state.copy(isMenuOpen = isOpen) }
        }

        private suspend fun showAttachment(isOpen: Boolean) {
            viewModelState.update { state -> state.copy(isAttachmentOpen = isOpen) }
        }

        private suspend fun deleteForever() {
            val noteId = uiState.value.wrapper.note.id ?: return
            deleteNoteUseCase.invoke(noteId)
            _navigationEvents.send(NoteDetailNavigationEvent.NavigateBack)
        }

        private suspend fun restore() {
            val noteId = uiState.value.wrapper.note.id ?: return
            restoreNoteFromTrashUseCase.invoke(noteId)
            loadNote(noteId)
        }

        private suspend fun copyNote() {
            val wrapper = uiState.value.wrapper
            val noteId = copyNoteUseCase.invoke(wrapper)
            loadNote(noteId)
        }

        private suspend fun close(moveToTrash: Boolean = false) {
            val postProcessedNote = prepareNoteForSave()
            val noteId = postProcessedNote.note.id ?: return

            if (postProcessedNote.isNoteWrapperInvalid()) {
                deleteNoteUseCase.invoke(noteId)
            } else {
                noteRepository.updateNote(postProcessedNote.note)
                if (moveToTrash) {
                    moveNoteToTrashUseCase.invoke(noteId)
                }
            }

            _navigationEvents.send(NoteDetailNavigationEvent.NavigateBack)
        }

        private suspend fun prepareNoteForSave(): NoteWrapper {
            return withContext(Dispatchers.IO) {
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

                postProcessedNote
            }
        }

        private suspend fun updateNoteContent(text: String) {
            note.update { note ->
                note.copy(
                    note = text,
                    editedAt = System.currentTimeMillis(),
                )
            }
        }

        private suspend fun toggleIsArchived() {
            val isArchived = note.value.isArchived.not();
            SnackbarController.sendEvent(
                SnackbarEvent(UiText.StringResources(AppStrings.Snack.noteArchived(isArchived)))
            )
            note.update { note -> note.copy(isArchived = isArchived) }
        }

        private suspend fun toggleIsPinned() {
            val isPinned = note.value.isPinned.not();
            SnackbarController.sendEvent(
                SnackbarEvent(UiText.StringResources(AppStrings.Snack.notePinned(isPinned)))
            )
            note.update { note -> note.copy(isPinned = isPinned) }
        }

        private suspend fun updateNoteTitle(text: String) {
            note.update { note -> note.copy(title = text, editedAt = System.currentTimeMillis()) }
        }

        private suspend fun addChecklistItem(checklist: Checklist) {
            val items = checklist.items.addNewChecklistItem()

            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)

                checklists
            }
        }

        private suspend fun deleteChecklist(checklist: Checklist) {
            checklists.update { checklists -> checklists.minus(checklist) }
            checklistRepository.deleteChecklistById(checklist.id ?: return)
        }

        private suspend fun deleteChecklistItem(
            checklist: Checklist,
            item: ChecklistItem,
        ) {
            val items = checklist.items.removeChecklistItem(item)

            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)
                checklists
            }
        }

        private suspend fun addChecklist() {
            val noteId = note.value.id ?: return

            val checklistId =
                checklistRepository.insertChecklist(
                    checklist =
                        Checklist.initialInstance(
                            noteId = noteId,
                            items = listOf(ChecklistItem.newInstance()),
                        ),
                )
            // TODO: Handle null
            val checklist = checklistRepository.getChecklistById(checklistId) ?: return
            checklists.update { checklists ->
                checklists.plus(checklist)
            }
        }

        private suspend fun updateChecklistName(checklist: Checklist, name: String) {
            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(name = name)
                checklists
            }
        }

        private suspend fun expandChecklist(checklist: Checklist, isExpanded: Boolean) {
            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(isExpanded = isExpanded)
                checklists
            }
        }

        private suspend fun updateChecklistItem(
            checklist: Checklist,
            oldItem: ChecklistItem,
            newItem: ChecklistItem,
        ) {
            checklists.update { state ->
                val checklists = state.toMutableList()
                val items = checklist.items.toMutableList()

                items[items.indexOf(oldItem)] = newItem
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)

                checklists
            }
        }

        private fun checkAllChecklistItems(checklist: Checklist, isCheck: Boolean) {
            val items = checklist.items.toggleAll(isCheck)

            checklists.update { state ->
                val checklists = state.toMutableList()
                checklists[checklists.indexOf(checklist)] = checklist.copy(items = items)
                checklists
            }
        }

        private suspend fun loadNote(noteId: Long) {
            // TODO: Handle that note not found
            val note = noteRepository.getNoteById(noteId) ?: return
            val checklists = checklistRepository.getChecklistsByNoteId(noteId)

            this@NoteDetailViewModel.note.update { note }
            this@NoteDetailViewModel.checklists.update { checklists }
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

sealed interface NoteDetailNavigationEvent {
    data object NavigateBack : NoteDetailNavigationEvent

    data class NavigateToReminderEdit(val noteId: Long, val reminderId: Long) : NoteDetailNavigationEvent

    data class NavigateLabelSelection(val id: Long) : NoteDetailNavigationEvent
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

    data object MoveToTrash : NoteDetailUiEvent()

    data object Close : NoteDetailUiEvent()

    data object CopyNote : NoteDetailUiEvent()

    data object DeleteForever : NoteDetailUiEvent()

    data object Restore : NoteDetailUiEvent()

    data object Undo : NoteDetailUiEvent()

    data object Redo : NoteDetailUiEvent()

    data class ShowMenu(val isOpen: Boolean) : NoteDetailUiEvent()

    data class ShowAttachment(val isOpen: Boolean) : NoteDetailUiEvent()

    data class ReminderClick(val reminder: Reminder) : NoteDetailUiEvent()

    data class LabelClick(val label: Label) : NoteDetailUiEvent()

    data object CreateReminder : NoteDetailUiEvent()
}

data class NoteDetailUiState(
    val wrapper: NoteWrapper = NoteWrapper(),
    val isReadOnly: Boolean = false,
    val isMenuOpen: Boolean = false,
    val isAttachmentOpen: Boolean = false
)

data class NoteDetailViewModelState(
    val isMenuOpen: Boolean = false,
    val isAttachmentOpen: Boolean = false
)
