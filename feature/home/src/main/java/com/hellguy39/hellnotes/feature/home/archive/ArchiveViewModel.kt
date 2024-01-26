package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.archive.GetAllArchivedNoteWrappers
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel
    @Inject
    constructor(
        getAllArchivedNoteWrappers: GetAllArchivedNoteWrappers,
        private val noteActionController: NoteActionController,
    ) : ViewModel() {
        private val _navigationEvents = Channel<ArchiveNavigationEvent>()
        val navigationEvents = _navigationEvents.receiveAsFlow()

        val uiState: StateFlow<ArchiveUiState> =
            combine(
                getAllArchivedNoteWrappers.invoke(),
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

        fun onNoteClick(index: Int) {
            viewModelScope.launch {
                val noteWrapper = uiState.value.selectableNoteWrappers[index]
                val noteId = noteWrapper.value.note.id ?: return@launch
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

sealed interface ArchiveNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : ArchiveNavigationEvent
}

data class ArchiveUiState(
    val countOfSelectedNotes: Int = 0,
    val selectableNoteWrappers: SnapshotStateList<Selectable<NoteDetailWrapper>> = mutableStateListOf(),
    val isEmpty: Boolean = false,
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
