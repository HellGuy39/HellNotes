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
package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.label.GetAllNoteWrappersByLabelId
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.toSelectable
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelViewModel
    @Inject
    constructor(
        private val labelRepository: LabelRepository,
        getAllNoteWrappersByLabelId: GetAllNoteWrappersByLabelId,
        savedStateHandle: SavedStateHandle,
        private val noteActionController: NoteActionController,
    ) : ViewModel() {
        private val singleUiEvents = Channel<LabelSingleUiEvent>()
        val singleUiEventFlow = singleUiEvents.receiveAsFlow()

        private val _navigationEvents = Channel<LabelNavigationEvent>()
        val navigationEvents = _navigationEvents.receiveAsFlow()

        private val labelId = savedStateHandle.getArgument(Arguments.LabelId)

        val uiState: StateFlow<LabelUiState> =
            combine(
                getAllNoteWrappersByLabelId.invoke(labelId),
                labelRepository.getLabelByIdFlow(labelId),
                labelRepository.getAllLabelsStream(),
                noteActionController.items,
            ) { noteWrappers, label, allLabels, selectedIds ->
                LabelUiState(
                    countOfSelectedNotes = selectedIds.size,
                    isEmpty = noteWrappers.isEmpty(),
                    noteWrappers = noteWrappers.toSelectable(selectedIds).toStateList(),
                    label = label ?: Label(),
                    allLabels = allLabels.toStateList(),
                )
            }
                .stateIn(
                    initialValue = LabelUiState(),
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )

        fun send(uiEvent: LabelUiEvent) {
            when (uiEvent) {
                is LabelUiEvent.NoteClick -> {
                    noteClick(uiEvent.noteId)
                }
                is LabelUiEvent.NotePress -> {
                    notePress(uiEvent.noteId)
                }
                is LabelUiEvent.DismissNote -> {
                    dismiss(uiEvent.noteSwipe, uiEvent.noteId)
                }
                is LabelUiEvent.DeleteLabel -> {
                    deleteLabel()
                }
                is LabelUiEvent.RenameLabel -> {
                    renameLabel(uiEvent.name)
                }
            }
        }

        private fun deleteLabel() {
            viewModelScope.launch {
                val label = uiState.value.label
                labelRepository.deleteLabel(label)
            }
        }

        private fun renameLabel(name: String) {
            viewModelScope.launch {
                val label = uiState.value.label.copy(name = name)
                labelRepository.updateLabel(label)
            }
        }

        private fun noteClick(noteId: Long?) {
            viewModelScope.launch {
                if (noteId == null) return@launch
                val selectedIds = noteActionController.items.value

                if (selectedIds.isEmpty()) {
                    _navigationEvents.send(LabelNavigationEvent.NavigateToNoteDetail(noteId))
                } else {
                    if (selectedIds.contains(noteId)) {
                        noteActionController.unselect(noteId)
                    } else {
                        noteActionController.select(noteId)
                    }
                }
            }
        }

        private fun notePress(noteId: Long?) {
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

        private fun dismiss(noteSwipe: NoteSwipe, noteId: Long?) {
            viewModelScope.launch {
                if (noteId == null) return@launch
                if (noteSwipe is NoteSwipe.None) return@launch

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

        fun isLabelUnique(name: String) =
            uiState.value.allLabels
                .find { label -> label.name == name } == null

        fun onDeleteSelectedItems() {
            viewModelScope.launch {
                noteActionController.moveToTrash()

                showNoteMovedToTrashSnackbar()
            }
        }

        fun onArchiveSelectedItems() {
            viewModelScope.launch {
                noteActionController.moveToTrash()

                showNoteArchivedSnackbar()
            }
        }

        fun onCancelItemSelection() {
            viewModelScope.launch {
                noteActionController.cancel()
            }
        }

        private suspend fun showNoteMovedToTrashSnackbar() {
            singleUiEvents.send(
                LabelSingleUiEvent.ShowSnackbar(
                    text = UiText.StringResources(AppStrings.Snack.NoteMovedToTrash),
                    action = { viewModelScope.launch { noteActionController.undo() } },
                ),
            )
        }

        private suspend fun showNoteArchivedSnackbar() {
            singleUiEvents.send(
                LabelSingleUiEvent.ShowSnackbar(
                    text = UiText.StringResources(AppStrings.Snack.NoteArchived),
                    action = { viewModelScope.launch { noteActionController.undo() } },
                ),
            )
        }
    }

sealed interface LabelSingleUiEvent {
    data class ShowSnackbar(val text: UiText, val action: () -> Unit) : LabelSingleUiEvent
}

sealed interface LabelNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : LabelNavigationEvent
}

sealed class LabelUiEvent {
    data class NoteClick(val noteId: Long?) : LabelUiEvent()

    data class NotePress(val noteId: Long?) : LabelUiEvent()

    data class DismissNote(val noteSwipe: NoteSwipe, val noteId: Long?) : LabelUiEvent()

    data class RenameLabel(val name: String) : LabelUiEvent()

    data object DeleteLabel : LabelUiEvent()
}

data class LabelUiState(
    val isEmpty: Boolean = false,
    val label: Label = Label(),
    val allLabels: SnapshotStateList<Label> = mutableStateListOf(),
    val noteWrappers: SnapshotStateList<Selectable<NoteWrapper>> = mutableStateListOf(),
    val countOfSelectedNotes: Int = 0,
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
