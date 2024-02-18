package com.hellguy39.hellnotes.feature.home.trash

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.trash.DeleteAllNoteWrappersAtTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.trash.GetAllNoteWrappersAtTrashFlowUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
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
class TrashViewModel
    @Inject
    constructor(
        getAllNoteWrappersAtTrashFlowUseCase: GetAllNoteWrappersAtTrashFlowUseCase,
        private val deleteAllNoteWrappersAtTrashUseCase: DeleteAllNoteWrappersAtTrashUseCase,
        private val dataStoreRepository: DataStoreRepository,
        private val noteActionController: NoteActionController,
    ) : ViewModel() {
        private val _navigationEvents = Channel<TrashNavigationEvent>()
        val navigationEvents = _navigationEvents.receiveAsFlow()

        val uiState: StateFlow<TrashUiState> =
            combine(
                getAllNoteWrappersAtTrashFlowUseCase.invoke(),
                noteActionController.items,
                dataStoreRepository.readTrashTipState(),
            ) { noteWrappersAtTrash, selectedIds, tipState ->
                TrashUiState(
                    countOfSelectedNotes = selectedIds.size,
                    trashTipCompleted = tipState,
                    selectableNoteWrappers =
                        noteWrappersAtTrash.toSelectable(selectedIds)
                            .toStateList(),
                    isEmpty = noteWrappersAtTrash.isEmpty(),
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = TrashUiState(),
                )

        fun onNoteClick(noteId: Long?) {
            viewModelScope.launch {
                if (noteId == null) return@launch
                val selectedIds = noteActionController.items.value

                if (selectedIds.isEmpty()) {
                    _navigationEvents.send(TrashNavigationEvent.NavigateToNoteDetail(noteId))
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

        fun trashTipCompleted(completed: Boolean) {
            viewModelScope.launch {
                dataStoreRepository.saveTrashTipState(completed)
            }
        }

        fun emptyTrash() {
            viewModelScope.launch {
                deleteAllNoteWrappersAtTrashUseCase.invoke()
            }
        }

        fun onRestoreSelectedItems() {
            viewModelScope.launch {
                noteActionController.restoreFromTrash()
            }
        }

        fun onDeleteForeverSelectedItems() {
            viewModelScope.launch {
                noteActionController.deleteForever()
            }
        }

        fun onCancelItemSelection() {
            viewModelScope.launch {
                noteActionController.cancel()
            }
        }
    }

sealed interface TrashNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : TrashNavigationEvent
}

data class TrashUiState(
    val countOfSelectedNotes: Int = 0,
    val trashTipCompleted: Boolean = true,
    val selectableNoteWrappers: SnapshotStateList<Selectable<NoteWrapper>> = mutableStateListOf(),
    val isEmpty: Boolean = false,
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
