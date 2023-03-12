package com.hellguy39.hellnotes.feature.home.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.toNoteDetailWrapper
import com.hellguy39.hellnotes.core.model.util.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    noteRepository: NoteRepository,
    labelRepository: LabelRepository,
    reminderRepository: ReminderRepository,
    private val dataStoreRepository: DataStoreRepository,
): ViewModel() {

    val uiState = combine(
            dataStoreRepository.readListSortState(),
            noteRepository.getAllNotesStream(),
            reminderRepository.getAllRemindersStream(),
            labelRepository.getAllLabelsStream()
        ) { sorting, notes, reminders, labels ->

            val sortedNotes = when(sorting) {
                is Sorting.DateOfCreation -> notes.sortedByDescending { note -> note.id }
                is Sorting.DateOfLastEdit -> notes.sortedByDescending { note -> note.editedAt }
            }
                .filter { note -> !note.isArchived }
                .map { note -> note.toNoteDetailWrapper(reminders, labels) }

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
