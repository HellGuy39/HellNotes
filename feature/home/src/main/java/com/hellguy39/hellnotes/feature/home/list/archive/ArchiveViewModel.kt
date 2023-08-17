package com.hellguy39.hellnotes.feature.home.list.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllArchivedWrappedNotesStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    getAllArchivedWrappedNotesStreamUseCase: GetAllArchivedWrappedNotesStreamUseCase
): ViewModel() {

    val uiState = getAllArchivedWrappedNotesStreamUseCase.invoke()
        .map { noteWrappers ->
            if (noteWrappers.isEmpty()) {
                ArchiveUiState.Empty
            } else {
                ArchiveUiState.Success(noteWrappers)
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ArchiveUiState.Idle
        )

}

sealed class ArchiveUiState {

    data object Idle: ArchiveUiState()

    data object Empty: ArchiveUiState()

    data class Success(val archivedNoteWrappers: List<NoteWrapper>): ArchiveUiState()

}