package com.hellguy39.hellnotes.notes.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.domain.note.IsNoteValidUseCase
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.util.KEY_NOTE_ID
import com.hellguy39.hellnotes.notes.util.NEW_NOTE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val isNoteValidUseCase: IsNoteValidUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState: MutableStateFlow<NoteDetailUiState> = MutableStateFlow(NoteDetailUiState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Int>(KEY_NOTE_ID)?.let { id ->
            if(id != NEW_NOTE_ID) {
                fetchNote(id)
            }
        }
    }

    private fun fetchNote(id: Int) = viewModelScope.launch {
        val note = repository.getNoteById(id)

        _uiState.update {
            NoteDetailUiState.Success(note)
        }
    }

    fun insertNote(note: Note) = viewModelScope.launch {
        if (isNoteValidUseCase.invoke(note)) {
            repository.insertNote(note)
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        if (isNoteValidUseCase.invoke(note)) {
            repository.updateNote(note)
        } else {
            note.id.let { id ->
                if (id != null && id != NEW_NOTE_ID) {
                    repository.deleteNoteById(id)
                }
            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun deleteNoteById(id: Int) = viewModelScope.launch {
        repository.deleteNoteById(id)
    }

}

sealed interface NoteDetailUiState {
    data class Success(val note: Note) : NoteDetailUiState
    object Empty: NoteDetailUiState
    object Loading: NoteDetailUiState
}