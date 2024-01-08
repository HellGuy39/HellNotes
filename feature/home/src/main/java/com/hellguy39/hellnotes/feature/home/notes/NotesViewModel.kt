package com.hellguy39.hellnotes.feature.home.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNotesWithRemindersAndLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.repository.local.database.removeCompletedChecklists
import com.hellguy39.hellnotes.core.model.repository.local.database.sortByPriority
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
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
                        isEmpty = pinnedNotes.isEmpty() && unpinnedNotes.isEmpty(),
                        noteCategories =
                            mutableStateListOf(
                                NoteCategory(
                                    title = UiText.StringResources(AppStrings.Label.Pinned),
                                    notes = pinnedNotes.toStateList(),
                                ),
                                NoteCategory(
                                    title = UiText.StringResources(AppStrings.Label.Others),
                                    notes = unpinnedNotes.toStateList(),
                                ),
                            ),
                    )
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = NoteListUiState(),
                )
    }

data class NoteListUiState(
    val isEmpty: Boolean = false,
    val noteCategories: SnapshotStateList<NoteCategory> = mutableStateListOf(),
)
