package com.hellguy39.hellnotes.feature.home.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.trash.EmptyTrashUseCase
import com.hellguy39.hellnotes.core.domain.use_case.trash.GetNotesFromTrashStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    getNotesFromTrashStreamUseCase: GetNotesFromTrashStreamUseCase,
    private val emptyTrashUseCase: EmptyTrashUseCase
): ViewModel() {

    val uiState = getNotesFromTrashStreamUseCase.invoke()
        .map { noteWrappers ->
            if (noteWrappers.isEmpty()) {
                TrashUiState.Empty
            } else {
                TrashUiState.Success(noteWrappers)
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TrashUiState.Idle
            )

    fun send(uiEvent: TrashScreenUiEvent) {
        when(uiEvent) {
            TrashScreenUiEvent.EmptyTrash -> emptyTrash()
        }
    }

    private fun emptyTrash() {
        viewModelScope.launch {
            emptyTrashUseCase.invoke()
        }
    }

}

sealed class TrashScreenUiEvent {

    object EmptyTrash: TrashScreenUiEvent()

}

sealed class TrashUiState {

    object Idle: TrashUiState()

    object Empty: TrashUiState()

    data class Success(val trashedNotes: List<NoteWrapper>): TrashUiState()

}