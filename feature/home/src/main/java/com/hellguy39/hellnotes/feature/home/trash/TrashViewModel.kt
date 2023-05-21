package com.hellguy39.hellnotes.feature.home.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.domain.use_case.DeleteExpiredNotesUseCase
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val trashRepository: TrashRepository,
    private val dataStoreRepository: DataStoreRepository,
): ViewModel() {

    private val _selectedNote = MutableStateFlow(Note())
    val selectedNote = _selectedNote.asStateFlow()

    val uiState: StateFlow<TrashUiState> =
        combine(
            trashRepository.getAllTrashStream(),
            dataStoreRepository.readTrashTipState()
        ) { trashes, tipState ->
            TrashUiState(
                trashTipCompleted = tipState,
                trashNotes = trashes.map { trash -> NoteDetailWrapper(note = trash.note) }
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TrashUiState.initialInstance()
            )

    fun trashTipCompleted(completed: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveTrashTipState(completed)
        }
    }

    fun selectNote(note: Note) {
        viewModelScope.launch {
            _selectedNote.update { note }
        }
    }

    fun clearSelectedNote() {
        viewModelScope.launch {
            _selectedNote.update { Note() }
        }
    }

    fun emptyTrash() {
        viewModelScope.launch {
            trashRepository.deleteAll()
        }
    }

}

data class TrashUiState(
    val trashTipCompleted: Boolean,
    val trashNotes: List<NoteDetailWrapper>,
) {
    companion object {
        fun initialInstance() = TrashUiState(
            trashTipCompleted = true,
            trashNotes = listOf(),
        )
    }
}