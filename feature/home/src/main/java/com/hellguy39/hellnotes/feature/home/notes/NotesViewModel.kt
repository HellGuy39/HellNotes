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
package com.hellguy39.hellnotes.feature.home.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.notes.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersFlowUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.removeCompletedChecklists
import com.hellguy39.hellnotes.core.model.repository.local.database.sortByPriority
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.toSelectable
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarAction
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarController
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarEvent
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.wrapper.UiPartition
import com.hellguy39.hellnotes.core.ui.wrapper.UiVolume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel
    @Inject
    constructor(
        getAllNoteWrappersFlowUseCase: GetAllNoteWrappersFlowUseCase,
        private val noteActionController: NoteActionController,
    ) : ViewModel() {

        private val navigationEvents = Channel<NotesNavigationEvent>()
        val navigationEventsFlow = navigationEvents.receiveAsFlow()

        val uiState =
            getAllNoteWrappersFlowUseCase.invoke()
                .combine(noteActionController.items) { noteWrappers, selectedIds ->
                    val sortedNotes =
                        noteWrappers
                            .sortedByDescending { wrapper -> wrapper.note.editedAt }
                            .filter { wrapper -> !wrapper.note.isArchived }
                            .map { noteDetailWrapper ->
                                val filteredChecklists =
                                    noteDetailWrapper.checklists
                                        .removeCompletedChecklists()
                                        .sortByPriority()
                                noteDetailWrapper.copy(checklists = filteredChecklists)
                            }

                    val (pinnedNotes, unpinnedNotes) = sortedNotes.partition { it.note.isPinned }

                    NoteListUiState(
                        countOfSelectedNotes = selectedIds.size,
                        isEmpty = pinnedNotes.isEmpty() && unpinnedNotes.isEmpty(),
                        noteVolume =
                            UiVolume(
                                partitions =
                                    mutableStateListOf(
                                        UiPartition(
                                            name = UiText.StringResources(AppStrings.Label.Pinned),
                                            elements = pinnedNotes.toSelectable(selectedIds).toStateList(),
                                        ),
                                        UiPartition(
                                            name = UiText.StringResources(AppStrings.Label.Others),
                                            elements = unpinnedNotes.toSelectable(selectedIds).toStateList(),
                                        ),
                                    ),
                            ),
                    )
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = NoteListUiState(),
                )

        fun onNoteClick(noteId: Long?) {
            viewModelScope.launch {
                if (noteId == null) return@launch
//                val noteWrapper = uiState.value.noteVolume.getElementByPositionInfo(positionInfo)
//                val noteId = noteWrapper.value.note.id ?: return@launch
                val selectedIds = noteActionController.items.value

                if (selectedIds.isEmpty()) {
                    navigationEvents.send(NotesNavigationEvent.NavigateToNoteDetail(noteId))
                } else {
                    if (selectedIds.contains(noteId)) {
                        noteActionController.unselect(noteId)
                    } else {
                        noteActionController.select(noteId)
                    }
                }
            }
        }

        fun onNotePress(noteId: Long?) {
            viewModelScope.launch {
                if (noteId == null) return@launch
//                val noteWrapper = uiState.value.noteVolume.getElementByPositionInfo(positionInfo)
//                val noteId = noteWrapper.value.note.id ?: return@launch
                val buffer = noteActionController.items.value

                if (buffer.contains(noteId)) {
                    noteActionController.unselect(noteId)
                } else {
                    noteActionController.select(noteId)
                }
            }
        }

        fun onNoteDismiss(noteSwipe: NoteSwipe, noteId: Long?) {
            viewModelScope.launch {
                if (noteId == null) return@launch
                if (noteSwipe is NoteSwipe.None) return@launch

                // val noteWrapper = uiState.value.noteVolume.getElementByPositionInfo(positionInfo)
                // val noteId = noteWrapper.value.note.id ?: return@launch

                when (noteSwipe) {
                    is NoteSwipe.Archive -> {
                        showNoteArchivedSnackbar()
                    }
                    is NoteSwipe.Delete -> {
                        showNoteMovedToTrashSnackbar()
                    }
                    else -> Unit
                }

                noteActionController.handleSwipe(noteSwipe, noteId)
            }
        }

        fun onDeleteSelectedItems() {
            viewModelScope.launch {
                noteActionController.moveToTrash()

                showNoteMovedToTrashSnackbar()
            }
        }

        fun onArchiveSelectedItems() {
            viewModelScope.launch {
                noteActionController.archive(isArchived = true)

                showNoteArchivedSnackbar()
            }
        }

        fun onCancelItemSelection() {
            viewModelScope.launch {
                noteActionController.cancel()
            }
        }

        private suspend fun showNoteMovedToTrashSnackbar() {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    text = UiText.StringResources(AppStrings.Snack.NoteMovedToTrash),
                    action = SnackbarAction.undoAction { viewModelScope.launch { noteActionController.undo() } },
                )
            )
        }

        private suspend fun showNoteArchivedSnackbar() {
            SnackbarController.sendEvent(
                SnackbarEvent(
                    text = UiText.StringResources(AppStrings.Snack.NoteArchived),
                    action = SnackbarAction.undoAction { viewModelScope.launch { noteActionController.undo() } },
                ),
            )
        }
    }

sealed interface NotesNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : NotesNavigationEvent
}

data class NoteListUiState(
    val countOfSelectedNotes: Int = 0,
    val isEmpty: Boolean = false,
    val noteVolume: UiVolume<Selectable<NoteWrapper>> = UiVolume(mutableStateListOf()),
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
