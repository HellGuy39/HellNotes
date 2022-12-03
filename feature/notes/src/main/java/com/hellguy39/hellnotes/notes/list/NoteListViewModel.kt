package com.hellguy39.hellnotes.notes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private var getNotesJob: Job? = null

    private var listViewType: ListViewType = ListViewType.Grid

    init {
        fetchNotes()
    }

    private fun fetchNotes() = viewModelScope.launch {
        getNotesJob?.cancel()
        getNotesJob = repository.getAllNotes()
            .onEach { notes ->
                if (notes.isNotEmpty())
                    _uiState.update {
                        NoteListUiState.Success(
                            pinnedNotes = notes.filter { it.isPinned },
                            notes = notes.filter { !it.isPinned },
                            listType = listViewType
                        )
                    }
                else
                    _uiState.update { NoteListUiState.Empty }
            }
            .launchIn(viewModelScope)
    }

    fun updateListViewType() {
        listViewType = if (listViewType == ListViewType.Grid)
            ListViewType.List
        else
            ListViewType.Grid

        _uiState.update { state ->
            when(state) {
                is NoteListUiState.Success -> {
                    state.copy(listType = listViewType)
                }
                else -> state
            }
        }
    }

}

sealed interface NoteListUiState {
    data class Success(
        val pinnedNotes: List<Note>,
        val notes: List<Note>,
        val listType: ListViewType
    ) : NoteListUiState
    object Empty : NoteListUiState
    object Loading : NoteListUiState
}

sealed interface ListViewType {
    object Grid : ListViewType
    object List : ListViewType
}