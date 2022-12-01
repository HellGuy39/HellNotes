package com.hellguy39.hellnotes.notes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        fetchNotes()
    }

    fun fetchNotes() = viewModelScope.launch {
        if (_uiState.value != NoteListUiState.Loading) {
            _uiState.update { NoteListUiState.Loading }
            val notes = repository.getAllNotes()
            _uiState.update { NoteListUiState.Success(notes) }
        }
    }

}

sealed interface NoteListUiState {
    data class Success(val notes: List<Note>) : NoteListUiState
    object Empty : NoteListUiState
    object Loading : NoteListUiState
}