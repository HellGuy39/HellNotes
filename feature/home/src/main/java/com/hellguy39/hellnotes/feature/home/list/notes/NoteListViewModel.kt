package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllWrappedPinnedNotesStreamUseCase
import com.hellguy39.hellnotes.core.domain.use_case.note.GetAllWrappedUnpinnedNotesStreamUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    getAllWrappedPinnedNotesStreamUseCase: GetAllWrappedPinnedNotesStreamUseCase,
    getAllWrappedUnpinnedNotesStreamUseCase: GetAllWrappedUnpinnedNotesStreamUseCase
): ViewModel() {

    val uiState = combine(
            getAllWrappedPinnedNotesStreamUseCase.invoke(),
            getAllWrappedUnpinnedNotesStreamUseCase.invoke()
        ) { pinnedNoteWrappers, unpinnedNoteWrappers ->
            val listState = if (pinnedNoteWrappers.isEmpty() && unpinnedNoteWrappers.isEmpty()) {
                NoteListState.Empty
            } else {
                NoteListState.Success(
                    pinnedNoteWrappers = pinnedNoteWrappers,
                    unpinnedNoteWrappers = unpinnedNoteWrappers,
                )
            }

            NotesUiState(
                listState = listState
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotesUiState()
        )
}

data class NotesUiState(
    val listState: NoteListState = NoteListState.Idle,
)

sealed class NoteListState {

    object Idle: NoteListState()

    object Empty: NoteListState()

    data class Success(
        val pinnedNoteWrappers: List<NoteWrapper>,
        val unpinnedNoteWrappers: List<NoteWrapper>,
    ): NoteListState()

}