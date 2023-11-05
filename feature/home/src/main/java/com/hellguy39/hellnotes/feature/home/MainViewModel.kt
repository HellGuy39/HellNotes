package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.note.InsertNoteUseCase
import com.hellguy39.hellnotes.core.model.local.database.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
): ViewModel() {

    private val _isSearchBarOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSearchBarOpen = _isSearchBarOpen.asStateFlow()

    private val _openedNoteId: MutableStateFlow<Long> = MutableStateFlow(Note.EMPTY_ID)
    val openedNoteId = _openedNoteId.asStateFlow()

    private val _isDetailOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDetailOpen = _isDetailOpen.asStateFlow()

    fun onEvent(uiEvent: MainUiEvent) {
        when(uiEvent) {
            is MainUiEvent.OpenNoteEditing -> {
                _openedNoteId.update { uiEvent.noteId }
                _isDetailOpen.update { true }
            }
            MainUiEvent.CloseNoteEditing -> {
                _openedNoteId.update { Note.EMPTY_ID }
                _isDetailOpen.update { false }
            }
            MainUiEvent.CreateNewNoteAndOpenEditing -> {
                viewModelScope.launch {
                    val newNoteId = insertNoteUseCase.invoke(Note())
                    _openedNoteId.update { newNoteId }
                    _isDetailOpen.update { true }
                }
            }
            is MainUiEvent.OpenSearchBar -> {
                _isSearchBarOpen.update { uiEvent.isOpen }
            }
        }
    }
}

sealed class MainUiEvent {

    data class OpenNoteEditing(val noteId: Long): MainUiEvent()

    data object CloseNoteEditing: MainUiEvent()

    data object CreateNewNoteAndOpenEditing: MainUiEvent()

    data class OpenSearchBar(val isOpen: Boolean): MainUiEvent()

}