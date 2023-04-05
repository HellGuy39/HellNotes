package com.hellguy39.hellnotes.feature.home.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.use_case.GetAllNotesWithRemindersAndLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.util.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    getAllNotesWithRemindersAndLabelsStreamUseCase: GetAllNotesWithRemindersAndLabelsStreamUseCase,
    private val dataStoreRepository: DataStoreRepository,
): ViewModel() {

    val uiState =
        combine(
            dataStoreRepository.readListSortState(),
            getAllNotesWithRemindersAndLabelsStreamUseCase.invoke()
        ) { sorting, notes ->

            val sortedNotes = notes
                .sortedByDescending { wrapper -> wrapper.note.editedAt }
                .filter { wrapper -> !wrapper.note.isArchived }

//            when(sorting) {
//                is Sorting.DateOfCreation -> notes.sortedByDescending { wrapper -> wrapper.note.id }
//                is Sorting.DateOfLastEdit -> notes.sortedByDescending { wrapper -> wrapper.note.editedAt }
//            }
//                .filter { wrapper -> !wrapper.note.isArchived }

            NoteListUiState(
                sorting = sorting,
                pinnedNotes = sortedNotes.filter { note -> note.note.isPinned },
                unpinnedNotes = sortedNotes.filter { note -> !note.note.isPinned },
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteListUiState.initialInstance()
        )

    fun updateSorting(sorting: Sorting) {
        viewModelScope.launch {
            dataStoreRepository.saveListSortState(sorting)
        }
    }

}

data class NoteListUiState(
    val sorting: Sorting,
    val isLoading: Boolean,
    val pinnedNotes: List<NoteDetailWrapper>,
    val unpinnedNotes: List<NoteDetailWrapper>,
) {
    companion object {
        fun initialInstance() = NoteListUiState(
            sorting = Sorting.DateOfCreation,
            isLoading = true,
            pinnedNotes = listOf(),
            unpinnedNotes = listOf()
        )
    }
}
