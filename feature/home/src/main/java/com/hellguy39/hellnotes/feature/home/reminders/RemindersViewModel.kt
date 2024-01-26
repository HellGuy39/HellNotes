package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.reminder.GetAllNoteWrappersWithRemindersUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.toSelectable
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
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
class RemindersViewModel
    @Inject
    constructor(
        getAllNoteWrappersWithRemindersUseCase: GetAllNoteWrappersWithRemindersUseCase,
        private val noteActionController: NoteActionController,
    ) : ViewModel() {
        private val _navigationEvents = Channel<RemindersNavigationEvent>()
        val navigationEvents = _navigationEvents.receiveAsFlow()

        val uiState: StateFlow<RemindersUiState> =
            getAllNoteWrappersWithRemindersUseCase.invoke()
                .combine(noteActionController.items) { noteWrappers, selectedIds ->
                    RemindersUiState(
                        countOfSelectedNotes = selectedIds.size,
                        selectableNoteWrappers = noteWrappers.toSelectable(selectedIds).toStateList(),
                        isEmpty = noteWrappers.isEmpty(),
                    )
                }
                .stateIn(
                    initialValue = RemindersUiState(),
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )

        fun onNoteClick(index: Int) {
            viewModelScope.launch {
                val noteWrapper = uiState.value.selectableNoteWrappers[index]
                val noteId = noteWrapper.value.note.id ?: return@launch
                val selectedIds = noteActionController.items.value

                if (selectedIds.isEmpty()) {
                    _navigationEvents.send(RemindersNavigationEvent.NavigateToNoteDetail(noteId))
                } else {
                    if (selectedIds.contains(noteId)) {
                        noteActionController.unselect(noteId)
                    } else {
                        noteActionController.select(noteId)
                    }
                }
            }
        }

        fun onNotePress(index: Int) {
            viewModelScope.launch {
                val noteWrapper = uiState.value.selectableNoteWrappers[index]
                val noteId = noteWrapper.value.note.id ?: return@launch
                val buffer = noteActionController.items.value

                if (buffer.contains(noteId)) {
                    noteActionController.unselect(noteId)
                } else {
                    noteActionController.select(noteId)
                }
            }
        }

        fun onNoteDismiss(noteSwipe: NoteSwipe, index: Int) {
            viewModelScope.launch {
                if (noteSwipe is NoteSwipe.None) return@launch

                val noteWrapper = uiState.value.selectableNoteWrappers[index]
                val noteId = noteWrapper.value.note.id ?: return@launch

                noteActionController.handleSwipe(noteSwipe, noteId)
            }
        }

        fun onDeleteSelectedItems() {
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

sealed interface RemindersNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : RemindersNavigationEvent
}

data class RemindersUiState(
    val countOfSelectedNotes: Int = 0,
    val isEmpty: Boolean = false,
    val selectableNoteWrappers: SnapshotStateList<Selectable<NoteDetailWrapper>> = mutableStateListOf(),
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
