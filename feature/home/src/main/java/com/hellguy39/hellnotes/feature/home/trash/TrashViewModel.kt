package com.hellguy39.hellnotes.feature.home.trash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.Trash
import com.hellguy39.hellnotes.core.ui.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val trashRepository: TrashRepository,
    private val dateHelper: DateHelper
): ViewModel() {

    private val trashViewModelState = MutableStateFlow(TrashViewModelState())

    init {
        deleteAllExpiredNotes()
        viewModelScope.launch {
            launch {
                trashRepository.getAllTrashStream().collect { trashNotes ->
                    trashViewModelState.update {
                        it.copy(trashNotes = trashNotes)
                    }
                }
            }
        }
    }

    val uiState = trashViewModelState
        .map(TrashViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            trashViewModelState.value.toUiState()
        )

    fun restoreSelectedNotes() {
        viewModelScope.launch {
            trashViewModelState.value.selectedNotes.forEach { note ->
                trashRepository.deleteTrashByNote(note)
                noteRepository.insertNote(note)
            }
            trashViewModelState.update {
                it.copy(selectedNotes = listOf())
            }
        }
    }

    fun emptyTrash() {
        viewModelScope.launch {
            trashRepository.deleteAllTrash()
        }
    }

    fun selectNote(note: Note){
        viewModelScope.launch {
            trashViewModelState.update {
                it.copy(selectedNotes = it.selectedNotes.plus(note))
            }
        }
    }

    fun unselectNote(note: Note) {
        viewModelScope.launch {
            trashViewModelState.update {
                it.copy(selectedNotes = it.selectedNotes.minus(note))
            }
        }
    }

    fun cancelNoteSelection() {
        viewModelScope.launch {
            trashViewModelState.update {
                it.copy(selectedNotes = listOf())
            }
        }
    }

    fun deleteSelectedNotes() {
        viewModelScope.launch {
            trashViewModelState.value.selectedNotes.forEach { note ->
                trashRepository.deleteTrashByNote(note)
            }

            trashViewModelState.update {
                it.copy(selectedNotes = listOf())
            }
        }
    }

    private fun deleteAllExpiredNotes() {
        viewModelScope.launch {
            trashRepository.getAllTrash().forEach { trash ->

                val expirationDate = trash.dateOfAdding + ((3600 * 1000) * (24 * 7))

                if (dateHelper.getCurrentTimeInEpochMilli() > expirationDate) {
                    trashRepository.deleteTrash(trash)
                }
            }
        }
    }

}

private data class TrashViewModelState(
    val trashNotes: List<Trash> = listOf(),
    val selectedNotes: List<Note> = listOf()
) {
    fun toUiState() = TrashUiState(
        trashNotes = trashNotes.map {
            NoteDetailWrapper(
                note = it.note,
                labels = listOf(),
                reminders = listOf()
            )
        },
        selectedNotes = selectedNotes,
    )
}

data class TrashUiState(
    val trashNotes: List<NoteDetailWrapper>,
    val selectedNotes: List<Note>
)