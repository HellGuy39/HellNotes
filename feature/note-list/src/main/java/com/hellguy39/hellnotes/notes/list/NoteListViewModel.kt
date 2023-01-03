package com.hellguy39.hellnotes.notes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.AppSettingsRepository
import com.hellguy39.hellnotes.data.repository.LabelRepository
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.data.repository.RemindRepository
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
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
    private val labelRepository: LabelRepository,
    private val remindRepository: RemindRepository,
    private val appSettingsRepository: AppSettingsRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _reminds: MutableStateFlow<List<Remind>> = MutableStateFlow(listOf())
    val reminds = _reminds.asStateFlow()

    private val _labels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
    val labels = _labels.asStateFlow()

    private var getNotesJob: Job? = null
    private var getLabelsJob: Job? = null
    private var getRemindersJob: Job? = null

    init {
        val sorting = appSettingsRepository.getListSort()
        fetchNotes(sorting)
        fetchLabels()
        fetchReminds()
    }

    private fun fetchReminds() = viewModelScope.launch {
        getRemindersJob?.cancel()
        getRemindersJob = remindRepository.getAllReminds()
            .onEach { reminds ->
                _reminds.update { reminds }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchLabels(query: String = "") = viewModelScope.launch {
        getLabelsJob?.cancel()
        getLabelsJob = labelRepository.getAllLabelsStream(query)
            .onEach { labels ->
                _labels.update { labels }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchNotes(sorting: Sorting) = viewModelScope.launch {
        getNotesJob?.cancel()
        getNotesJob = noteRepository.getAllNotesStream()
            .onEach { notes ->
                if (notes.isNotEmpty()) {

                    val sortedNotes = when(sorting) {
                        is Sorting.DateOfCreation -> {
                            notes.sortedByDescending { it.id }
                        }
                        is Sorting.DateOfLastEdit -> {
                            notes.sortedByDescending { it.lastEditDate }
                        }
                    }

                    _uiState.update {
                        NoteListUiState.Success(
                            sorting = appSettingsRepository.getListSort(),
                            listStyle = appSettingsRepository.getListStyle(),
                            pinnedNotes = sortedNotes.filter { it.isPinned },
                            notes = sortedNotes.filter { !it.isPinned },
                            selectedNotes = listOf()
                        )
                    }
                } else {
                    _uiState.update { NoteListUiState.Empty }
                }
            }
            .launchIn(viewModelScope)
    }

    fun deleteAllSelected() = viewModelScope.launch {
        _uiState.value.let { state ->
            if (state is NoteListUiState.Success) {
                noteRepository.deleteNotes(state.selectedNotes)
            }
        }
    }

    fun updateListStyle() = viewModelScope.launch {
        _uiState.let { stateFlow ->
            if (stateFlow.value is NoteListUiState.Success) {

                val currentStyle = (stateFlow.value as NoteListUiState.Success).listStyle
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

    fun isSelection() = this is Success && this.selectedNotes.isNotEmpty()

    fun selectedCount() = if (this is Success) this.selectedNotes.count() else 0

}