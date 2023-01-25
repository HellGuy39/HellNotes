package com.hellguy39.hellnotes.feature.home.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.model.Trash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val trashRepository: TrashRepository
): ViewModel() {

    val uiState = trashRepository.getAllTrashStream()
        .map {
            TrashUiState(
                trashNotes = it.sortedByDescending { trash -> trash.dateOfAdding },
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            TrashUiState(
                trashNotes = listOf()
            )
        )


    fun restoreNote(trash: Trash) {
        viewModelScope.launch {
            trashRepository.deleteTrash(trash)
            noteRepository.insertNote(trash.note)
        }
    }

    fun restoreSelectedNotes() {

    }

    fun emptyTrash() {
        viewModelScope.launch {
            trashRepository.deleteAllTrash()
        }
    }

}

data class TrashUiState(
    val trashNotes: List<Trash>
)