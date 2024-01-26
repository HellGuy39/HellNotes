package com.hellguy39.hellnotes.feature.home.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNoteWrappersUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.removeCompletedChecklists
import com.hellguy39.hellnotes.core.model.repository.local.database.sortByPriority
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.model.toSelectable
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.wrapper.PartitionElementPositionInfo
import com.hellguy39.hellnotes.core.ui.wrapper.UiPartition
import com.hellguy39.hellnotes.core.ui.wrapper.UiVolume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel
    @Inject
    constructor(
        getAllNoteWrappersUseCase: GetAllNoteWrappersUseCase,
        private val noteActionController: NoteActionController,
    ) : ViewModel() {
//        val singleEvents =
//            noteActionController.singleEvents
//                .map { event ->
//                    when (event) {
//                        is NoteActionController.SingleEvent.ExecutedAction -> {
//                            when (event.action) {
//                                is NoteActionController.Action.Delete ->
//                                    NotesSingleEvent.ShowSnackbar(
//                                        text = UiText.StringResources(AppStrings.Snack.NoteMovedToTrash),
//                                        action = { viewModelScope.launch { noteActionController.undo() } },
//                                    )
//                                is NoteActionController.Action.Archive ->
//                                    NotesSingleEvent.ShowSnackbar(
//                                        text = UiText.StringResources(AppStrings.Snack.NoteArchived),
//                                        action = { viewModelScope.launch { noteActionController.undo() } },
//                                    )
//                                is NoteActionController.Action.Unarchive ->
//                                    NotesSingleEvent.ShowSnackbar(
//                                        text = UiText.StringResources(AppStrings.Snack.NoteUnarchived),
//                                        action = { viewModelScope.launch { noteActionController.undo() } },
//                                    )
//                                else -> NotesSingleEvent.Idle
//                            }
//                        }
//                        else -> NotesSingleEvent.Idle
//                    }
//                }

        private val _navigationEvents = Channel<NotesNavigationEvent>()
        val navigationEvents = _navigationEvents.receiveAsFlow()

        val uiState =
            getAllNoteWrappersUseCase.invoke()
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

        fun onNoteClick(positionInfo: PartitionElementPositionInfo) {
            viewModelScope.launch {
                val noteWrapper = uiState.value.noteVolume.getElementByPositionInfo(positionInfo)
                val noteId = noteWrapper.value.note.id ?: return@launch
                val selectedIds = noteActionController.items.value

                if (selectedIds.isEmpty()) {
                    _navigationEvents.send(NotesNavigationEvent.NavigateToNoteDetail(noteId))
                } else {
                    if (selectedIds.contains(noteId)) {
                        noteActionController.unselect(noteId)
                    } else {
                        noteActionController.select(noteId)
                    }
                }
            }
        }

        fun onNotePress(positionInfo: PartitionElementPositionInfo) {
            viewModelScope.launch {
                val noteWrapper = uiState.value.noteVolume.getElementByPositionInfo(positionInfo)
                val noteId = noteWrapper.value.note.id ?: return@launch
                val buffer = noteActionController.items.value

                if (buffer.contains(noteId)) {
                    noteActionController.unselect(noteId)
                } else {
                    noteActionController.select(noteId)
                }
            }
        }

        fun onNoteDismiss(noteSwipe: NoteSwipe, positionInfo: PartitionElementPositionInfo) {
            viewModelScope.launch {
                if (noteSwipe is NoteSwipe.None) return@launch

                val noteWrapper = uiState.value.noteVolume.getElementByPositionInfo(positionInfo)
                val noteId = noteWrapper.value.note.id ?: return@launch

                noteActionController.handleSwipe(noteSwipe, noteId)
            }
        }

        fun onDeleteSelectedItems() {
            viewModelScope.launch {
                noteActionController.deleteSelected()
            }
        }

        fun onArchiveSelectedItems() {
            viewModelScope.launch {
                noteActionController.archiveSelected(isArchived = true)
            }
        }

        fun onCancelItemSelection() {
            viewModelScope.launch {
                noteActionController.cancel()
            }
        }
    }

sealed interface NotesSingleEvent {
    data class ShowSnackbar(val text: UiText, val action: () -> Unit) : NotesSingleEvent

    data object Idle : NotesSingleEvent
}

sealed interface NotesNavigationEvent {
    data class NavigateToNoteDetail(val noteId: Long) : NotesNavigationEvent
}

data class NoteListUiState(
    val countOfSelectedNotes: Int = 0,
    val isEmpty: Boolean = false,
    val noteVolume: UiVolume<Selectable<NoteDetailWrapper>> = UiVolume(mutableStateListOf()),
) {
    val isNoteSelection: Boolean
        get() = countOfSelectedNotes > 0
}
