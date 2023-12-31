package com.hellguy39.hellnotes.feature.home.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNotesWithRemindersAndLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.removeCompletedChecklists
import com.hellguy39.hellnotes.core.model.repository.local.database.sortByPriority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotesViewModel
    @Inject
    constructor(
        getAllNotesWithRemindersAndLabelsStreamUseCase: GetAllNotesWithRemindersAndLabelsStreamUseCase,
    ) : ViewModel() {
        val uiState =
            getAllNotesWithRemindersAndLabelsStreamUseCase.invoke()
                .map { notes ->
                    val sortedNotes =
                        notes
                            .sortedByDescending { wrapper -> wrapper.note.editedAt }
                            .filter { wrapper -> !wrapper.note.isArchived }
                            .map { noteDetailWrapper ->
                                val filteredChecklists =
                                    noteDetailWrapper.checklists
                                        .removeCompletedChecklists()
                                        .sortByPriority()
                                noteDetailWrapper.copy(checklists = filteredChecklists)
                            }

                    val pinnedNotes = sortedNotes.filter { note -> note.note.isPinned }
                    val unpinnedNotes = sortedNotes.filter { note -> !note.note.isPinned }

                    NoteListUiState(
                        pinnedNotes = pinnedNotes,
                        unpinnedNotes = unpinnedNotes,
                    )
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = NoteListUiState(),
                )
    }

data class NoteListUiState(
    val pinnedNotes: List<NoteDetailWrapper> = listOf(),
    val unpinnedNotes: List<NoteDetailWrapper> = listOf(),
)
