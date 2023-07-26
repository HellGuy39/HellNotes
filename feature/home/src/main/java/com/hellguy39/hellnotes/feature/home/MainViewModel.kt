package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.home.util.HomeScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
): ViewModel() {

    private val _isDetailOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDetailOpen = _isDetailOpen.asStateFlow()

    private val _openedNoteId: MutableStateFlow<Long> = MutableStateFlow(Note.EMPTY_ID)
    val openedNoteId = _openedNoteId.asStateFlow()

    fun closeNoteEdit() {
        viewModelScope.launch {
            _isDetailOpen.update { false }
            _openedNoteId.update { Note.EMPTY_ID }
        }
    }

    fun openNoteEdit(noteId: Long?) {
        if (noteId == null) return
        viewModelScope.launch {
            _isDetailOpen.update { true }
            _openedNoteId.update { noteId }
        }
    }

    fun newNote() {
        viewModelScope.launch {
            val id = noteRepository.insertNote(Note())
            openNoteEdit(id)
        }
    }
}