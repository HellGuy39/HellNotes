package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllWrappedPinnedNotesStreamUseCase
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllWrappedUnpinnedNotesStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    getAllWrappedPinnedNotesStreamUseCase: GetAllWrappedPinnedNotesStreamUseCase,
    getAllWrappedUnpinnedNotesStreamUseCase: GetAllWrappedUnpinnedNotesStreamUseCase
): ViewModel() {

    private val searchState = MutableStateFlow(NotesSearchState())

    private val notesListState = combine(
        searchState,
        getAllWrappedPinnedNotesStreamUseCase.invoke(),
        getAllWrappedUnpinnedNotesStreamUseCase.invoke()
    ) { searchState, pinnedNotes, unpinnedNotes ->
        val filteredPinnedNotes = pinnedNotes.applyQueryFilter(searchState.query)
        val filteredUnpinnedNotes = unpinnedNotes.applyQueryFilter(searchState.query)

        if (filteredPinnedNotes.isEmpty() && filteredUnpinnedNotes.isEmpty()) {
            NotesListState.Empty
        }

        NotesListState.Success(filteredPinnedNotes, filteredUnpinnedNotes)
    }

    val uiState = combine(
        searchState, notesListState
    ) { searchState, notesListState ->
            NotesUiState(
                searchState = searchState,
                notesListState = notesListState
            )
        }
        .stateIn(
            initialValue = NotesUiState(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    fun onEvent(uiEvent: NotesUiEvent) {
        when(uiEvent) {
            is NotesUiEvent.ChangeIsSearchActive -> {
                searchState.update { state ->
                    state.copy(isActive = uiEvent.isActive)
                }
            }
            is NotesUiEvent.ChangeSearchQuery -> {
                searchState.update { state ->
                    state.copy(query = uiEvent.query)
                }
            }
            is NotesUiEvent.Search -> {

            }
        }
    }
}

sealed class NotesUiEvent {

    data class ChangeSearchQuery(val query: String): NotesUiEvent()

    data class ChangeIsSearchActive(val isActive: Boolean): NotesUiEvent()

    data class Search(val query: String): NotesUiEvent()

}

data class NotesUiState(
    val searchState: NotesSearchState = NotesSearchState(),
    val notesListState: NotesListState = NotesListState.Idle
)

sealed class NotesListState {

    data object Idle: NotesListState()

    data object Empty: NotesListState()

    data class Success(
        val pinnedNoteWrappers: List<NoteWrapper>,
        val unpinnedNoteWrappers: List<NoteWrapper>,
    ): NotesListState()

}

fun List<NoteWrapper>.applyQueryFilter(q: String): List<NoteWrapper> {

    if (q.isEmpty())
        return this

    return this.filter { noteWrapper ->
        noteWrapper.note.note.contains(q, true) ||
                noteWrapper.note.title.contains(q, true)
    }
}

data class NotesSearchState(
    val query: String = "",
    val isActive: Boolean = false
)