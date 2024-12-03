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
package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.notes.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.archive.GetAllArchivedNoteWrappersFlowUseCase
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarAction
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarController
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarEvent
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel
    @Inject
    constructor(
        getAllArchivedNoteWrappersFlowUseCase: GetAllArchivedNoteWrappersFlowUseCase,
        private val noteActionController: NoteActionController,
    ) : ViewModel() {

        private val _navigationEvents = Channel<ArchiveNavigationEvent>()
        val navigationEvents = _navigationEvents.receiveAsFlow()

        val uiState: StateFlow<ArchiveUiState> =
            combine(
                getAllArchivedNoteWrappersFlowUseCase.invoke(),
                noteActionController.items,
            ) { noteWrappers, selectedIds ->
                ArchiveUiState(
                    countOfSelectedNotes = selectedIds.size,
                    selectableNoteWrappers = noteWrappers.toSelectable(selectedIds).toStateList(),
                    isEmpty = noteWrappers.isEmpty(),
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = ArchiveUiState(),
                )

        fun onNoteClick(noteId: Long?) {
            viewModelScope.launch {
                if (noteId == null) return@launch
                val selectedIds = noteActionController.items.value

                if (selectedIds.isEmpty()) {
                    _navigationEvents.send(ArchiveNavigationEvent.NavigateToNoteDetail(noteId))
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
                val buffer = noteActionController.items.value

                if (buffer.contains(noteId)) {
                    noteActionController.unselect(noteId)
                } else {
                    noteActionController.select(noteId)
                }
            }
        }

        fun onDeleteSelectedItems() {
            viewModelScope.launch {
                noteActionController.moveToTrash()

                SnackbarController.sendEvent(
                    SnackbarEvent(
                        text = UiText.StringResources(AppStrings.Snack.NoteMovedToTrash),
                        action = SnackbarAction.undoAction { viewModelScope.launch { noteActionController.undo() } },
                    )
                )
            }
        }

        fun onArchiveSelectedItems() {
            viewModelScope.launch {
                noteActionController.archive(false)

                SnackbarController.sendEvent(
                    SnackbarEvent(
                        text = UiText.StringResources(AppStrings.Snack.NoteUnarchived),
                        action = SnackbarAction.undoAction { viewModelScope.launch { noteActionController.undo() } },
                    )
                )
            }
        }

        fun onCancelItemSelection() {
            viewModelScope.launch {
                noteActionController.cancel()
            }
        }
    }

sealed interface ArchiveNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : ArchiveNavigationEvent
}

data class ArchiveUiState(
    val countOfSelectedNotes: Int = 0,
    val selectableNoteWrappers: SnapshotStateList<Selectable<NoteWrapper>> = mutableStateListOf(),
    val isEmpty: Boolean = false,
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
