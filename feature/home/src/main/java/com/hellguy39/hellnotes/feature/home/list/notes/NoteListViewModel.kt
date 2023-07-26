package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllWrappedPinnedNotesStreamUseCase
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllWrappedUnpinnedNotesStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    getAllWrappedPinnedNotesStreamUseCase: GetAllWrappedPinnedNotesStreamUseCase,
    getAllWrappedUnpinnedNotesStreamUseCase: GetAllWrappedUnpinnedNotesStreamUseCase
): ViewModel() {

    private val searchState = MutableStateFlow(SearchState())

    val uiState = combine(
        getAllWrappedPinnedNotesStreamUseCase.invoke(),
        getAllWrappedUnpinnedNotesStreamUseCase.invoke(),
        searchState
    ) { pinnedNoteWrappers, unpinnedNoteWrappers, searchState ->

        val filteredPinnedNoteWrappers = pinnedNoteWrappers.applyQueryFilter(searchState.query)
        val filteredUnpinnedNoteWrappers = unpinnedNoteWrappers.applyQueryFilter(searchState.query)

        val listState = if (filteredPinnedNoteWrappers.isEmpty() &&
            filteredUnpinnedNoteWrappers.isEmpty()) {
            NoteListState.Empty
        } else {
            NoteListState.Success(
                pinnedNoteWrappers = pinnedNoteWrappers,
                unpinnedNoteWrappers = unpinnedNoteWrappers,
            )
        }

        NotesUiState(
            listState = listState,
            searchState = searchState
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotesUiState()
        )

    fun searchIsActiveChange(b: Boolean) {
        viewModelScope.launch {
            searchState.update { state -> state.copy(isActive = b) }
        }
    }

    fun searchQueryChange(q: String) {
        viewModelScope.launch {
            searchState.update { state -> state.copy(query = q) }
        }
    }

    fun search(q: String) {
        viewModelScope.launch {

        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 300L
    }

}

data class NotesUiState(
    val listState: NoteListState = NoteListState.Idle,
    val searchState: SearchState = SearchState()
)

data class SearchState(
    val query: String = "",
    val isActive: Boolean = false
)

sealed class NoteListState {

    object Idle: NoteListState()

    object Empty: NoteListState()

    data class Success(
        val pinnedNoteWrappers: List<NoteWrapper>,
        val unpinnedNoteWrappers: List<NoteWrapper>,
    ): NoteListState()

}

fun List<NoteWrapper>.applyQueryFilter(q: String): List<NoteWrapper> {

    if (q.isEmpty())
        return this

    return this
}