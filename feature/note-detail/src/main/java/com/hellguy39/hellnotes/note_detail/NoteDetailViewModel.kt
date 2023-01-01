package com.hellguy39.hellnotes.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.data.repository.RemindRepository
import com.hellguy39.hellnotes.domain.note.IsNoteValidUseCase
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.util.KEY_NOTE_ID
import com.hellguy39.hellnotes.note_detail.util.NEW_NOTE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val isNoteValidUseCase: IsNoteValidUseCase,
    private val remindRepository: RemindRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())
    val note = _note.asStateFlow()

    init {
        savedStateHandle.get<Int>(KEY_NOTE_ID)?.let { id ->
            if(id != NEW_NOTE_ID) {
                fetchNote(id)
            }
        }
    }

    fun updateNoteContent(text: String) {
        val currentTime = Calendar.getInstance().time.time
        _note.update {
            it.copy(
                note = text,
                lastEditDate = currentTime
            )
        }
    }

    fun insertRemind(remind: Remind) = viewModelScope.launch {
        remindRepository.insertRemind(remind)
    }

    fun updateNoteTitle(text: String) {
        val currentTime = Calendar.getInstance().time.time

        _note.update {
            it.copy(
                title = text,
                lastEditDate = currentTime
            )
        }
    }

    fun updateNoteBackground(newColor: Long) = viewModelScope.launch {
        _note.update {
            it.copy(colorHex = newColor)
        }
    }

    fun updateIsPinned(isPinned: Boolean) = viewModelScope.launch {
        _note.update { it.copy(isPinned = isPinned) }
    }

    private fun fetchNote(id: Int) = viewModelScope.launch {
        val fetchedNote = noteRepository.getNoteById(id)

        _note.update { fetchedNote }
    }

    fun saveNote() = viewModelScope.launch {
        _note.value.let { note ->
            if (_note.value.id == null) {
                if (isNoteValidUseCase.invoke(note)) {
                    noteRepository.insertNote(note)
                }
            } else {
                if (isNoteValidUseCase.invoke(note)) {
                    noteRepository.updateNote(note)
                } else {
                    note.id.let { id ->
                        if (id != null && id != NEW_NOTE_ID) {
                            noteRepository.deleteNoteById(id)
                        }
                    }
                }
            }
        }
    }

    fun deleteNote() = viewModelScope.launch {
        _note.value.let {
            if (it.id != null)
                noteRepository.deleteNote(note.value)
        }
    }

}