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
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.toSelectable
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import com.hellguy39.hellnotes.feature.home.reminders.RemindersNavigationEvent
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
        private val _navigationEvents = Channel<RemindersNavigationEvent>()
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
                    selectableNoteWrappers = noteWrappers.toSelectable(selectedIds).toStateList(),
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
                    noteClick(uiEvent.index)
                }
                is LabelUiEvent.NotePress -> {
                    notePress(uiEvent.index)
                }
                is LabelUiEvent.DismissNote -> {
                    dismiss(uiEvent.noteSwipe, uiEvent.index)
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

        private fun noteClick(index: Int) {
            viewModelScope.launch {
            }
        }

        private fun notePress(index: Int) {
            viewModelScope.launch {
            }
        }

        private fun dismiss(noteSwipe: NoteSwipe, index: Int) {
            viewModelScope.launch {
            }
        }

        fun isLabelUnique(name: String) =
            uiState.value.allLabels
                .find { label -> label.name == name } == null

        fun onDeleteSelectedItems() {
            viewModelScope.launch {
                noteActionController.deleteSelected()
            }
        }

        fun onArchiveSelectedItems() {
            viewModelScope.launch {
                noteActionController.deleteSelected()
            }
        }

        fun onCancelItemSelection() {
            viewModelScope.launch {
                noteActionController.cancel()
            }
        }
    }

sealed interface LabelNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : LabelNavigationEvent
}

sealed class LabelUiEvent {
    data class NoteClick(val index: Int) : LabelUiEvent()

    data class NotePress(val index: Int) : LabelUiEvent()

    data class DismissNote(val noteSwipe: NoteSwipe, val index: Int) : LabelUiEvent()

    data class RenameLabel(val name: String) : LabelUiEvent()

    data object DeleteLabel : LabelUiEvent()
}

data class LabelUiState(
    val isEmpty: Boolean = false,
    val label: Label = Label(),
    val allLabels: SnapshotStateList<Label> = mutableStateListOf(),
    val selectableNoteWrappers: SnapshotStateList<Selectable<NoteDetailWrapper>> = mutableStateListOf(),
    val countOfSelectedNotes: Int = 0,
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
