package com.hellguy39.hellnotes.notes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.AppSettingsRepository
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.model.util.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val appSettingsRepository: AppSettingsRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        val sorting = appSettingsRepository.getListSort()
        fetchNotes(sorting)
    }

    private fun fetchNotes(sorting: Sorting) = viewModelScope.launch {
        getNotesJob?.cancel()
        getNotesJob = noteRepository.getAllNotesWithSorting(sorting)
            .onEach { notes ->
                if (notes.isNotEmpty()) {
                    _uiState.update {
                        NoteListUiState.Success(
                            sorting = appSettingsRepository.getListSort(),
                            listStyle = appSettingsRepository.getListStyle(),
                            pinnedNotes = notes.filter { it.isPinned },
                            notes = notes.filter { !it.isPinned },
                            selectedNotes = listOf()
                        )
                    }
                } else {
                    _uiState.update { NoteListUiState.Empty }
                }
            }
            .launchIn(viewModelScope)
    }

    fun updateListStyle() = viewModelScope.launch {
        _uiState.let { stateFlow ->
            if (stateFlow.value is NoteListUiState.Success) {

                val currentStyle = (stateFlow as NoteListUiState.Success).listStyle
                val newStyle = if (currentStyle == ListStyle.Grid)
                    ListStyle.Column
                else
                    ListStyle.Grid

                appSettingsRepository.saveListStyle(newStyle)

                stateFlow.update {
                    (it as NoteListUiState.Success).copy(
                        listStyle = newStyle
                    )
                }
            }
        }
    }

    fun updateSorting(sorting: Sorting) = viewModelScope.launch {
        appSettingsRepository.saveListSort(sorting)
        _uiState.let { stateFlow ->
            if (stateFlow.value is NoteListUiState.Success) {
                stateFlow.update {
                    (it as NoteListUiState.Success).copy(
                        sorting = sorting
                    )
                }
            }
        }
        fetchNotes(sorting)
    }

    fun selectNote(note: Note) {
        _uiState.let { stateFlow ->
            if (stateFlow.value is NoteListUiState.Success) {
                stateFlow.update {
                    (it as NoteListUiState.Success).copy(
                        selectedNotes = it.selectedNotes.plus(note)
                    )
                }
            }
        }
    }

    fun unselectNote(note: Note) {
        _uiState.let { stateFlow ->
            if (stateFlow.value is NoteListUiState.Success) {
                stateFlow.update {
                    (it as NoteListUiState.Success).copy(
                        selectedNotes = it.selectedNotes.minus(note)
                    )
                }
            }
        }
    }

    fun cancelNoteSelection() {
        _uiState.let { stateFlow ->
            if (stateFlow.value is NoteListUiState.Success) {
                stateFlow.update {
                    (it as NoteListUiState.Success).copy(
                        selectedNotes = listOf()
                    )
                }
            }
        }
    }

}

sealed interface NoteListUiState {
    data class Success(
        val sorting: Sorting,
        val listStyle: ListStyle,
        val pinnedNotes: List<Note>,
        val notes: List<Note>,
        val selectedNotes: List<Note>
    ) : NoteListUiState
    object Empty : NoteListUiState
    object Loading : NoteListUiState
}