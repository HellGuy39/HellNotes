package com.hellguy39.hellnotes.feature.note_swipe_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteSwipeEditScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    val uiState =  dataStoreRepository.readNoteSwipesState()
        .map { state ->
            NoteSwipeEditScreenUiState(
                noteSwipesState = state
            )
        }
        .stateIn(
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteSwipeEditScreenUiState(),
            scope = viewModelScope
        )

    fun send(event: NoteSwipeEditScreenUiEvent) {
        when(event) {
            is NoteSwipeEditScreenUiEvent.EnableNoteSwipes -> {
                saveNoteSwipesEnabled(event.enabled)
            }
            is NoteSwipeEditScreenUiEvent.SelectLeftAction -> {
                saveNoteSwipeLeft(event.noteSwipe)
            }
            is NoteSwipeEditScreenUiEvent.SelectRightAction -> {
                saveNoteSwipeRight(event.noteSwipe)
            }
        }
    }

    private fun saveNoteSwipeLeft(noteSwipe: NoteSwipe) {
        viewModelScope.launch {
            val state = uiState.value.noteSwipesState

            dataStoreRepository.saveNoteSwipesState(
                state.copy(swipeLeft = noteSwipe)
            )
        }
    }

    private fun saveNoteSwipeRight(noteSwipe: NoteSwipe) {
        viewModelScope.launch {
            val state = uiState.value.noteSwipesState

            dataStoreRepository.saveNoteSwipesState(
                state.copy(swipeRight = noteSwipe)
            )
        }
    }

    private fun saveNoteSwipesEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val state = uiState.value.noteSwipesState

            dataStoreRepository.saveNoteSwipesState(
                state.copy(enabled = enabled)
            )
        }
    }

}

data class NoteSwipeEditScreenUiState(
    val noteSwipesState: NoteSwipesState = NoteSwipesState(false, NoteSwipe.None, NoteSwipe.None)
)

sealed class NoteSwipeEditScreenUiEvent {

    data class SelectRightAction(val noteSwipe: NoteSwipe): NoteSwipeEditScreenUiEvent()

    data class SelectLeftAction(val noteSwipe: NoteSwipe): NoteSwipeEditScreenUiEvent()

    data class EnableNoteSwipes(val enabled: Boolean): NoteSwipeEditScreenUiEvent()

}