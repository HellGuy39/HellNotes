package com.hellguy39.hellnotes.feature.home.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllNotesWithRemindersAndLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.removeCompletedChecklists
import com.hellguy39.hellnotes.core.model.repository.local.database.sortByPriority
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel
@Inject
constructor(
    getAllNotesWithRemindersAndLabelsStreamUseCase: GetAllNotesWithRemindersAndLabelsStreamUseCase,
    private val dataStoreRepository: DataStoreRepository,
    val analyticsLogger: AnalyticsLogger
): ViewModel() {

    val uiState =
        combine(
            dataStoreRepository.readListSortState(),
            getAllNotesWithRemindersAndLabelsStreamUseCase.invoke()
        ) { sorting, notes ->

            val sortedNotes = notes
                .sortedByDescending { wrapper -> wrapper.note.editedAt }
                .filter { wrapper -> !wrapper.note.isArchived }
                .map { noteDetailWrapper ->
                    val filteredChecklists = noteDetailWrapper.checklists
                        .removeCompletedChecklists()
                        .sortByPriority()
                    noteDetailWrapper.copy(checklists = filteredChecklists)
                }

            val pinnedNotes = sortedNotes.filter { note -> note.note.isPinned }
            val unpinnedNotes = sortedNotes.filter { note -> !note.note.isPinned }

            NoteListUiState(
                sorting = sorting,
                pinnedNotes = pinnedNotes,
                unpinnedNotes = unpinnedNotes,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteListUiState()
        )

    fun updateSorting(sorting: Sorting) {
        viewModelScope.launch {
            dataStoreRepository.saveListSortState(sorting)
        }
    }
}

data class NoteListUiState(
    val sorting: Sorting = Sorting.DateOfCreation,
    val isLoading: Boolean = true,
    val pinnedNotes: List<NoteDetailWrapper> = listOf(),
    val unpinnedNotes: List<NoteDetailWrapper> = listOf(),
)
