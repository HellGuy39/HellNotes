package com.hellguy39.hellnotes.notes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val appSettingsRepository: AppSettingsRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<NoteListUiState> = MutableStateFlow(NoteListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val reminders: StateFlow<List<Remind>> = reminderRepository.getAllRemindsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    val labels: StateFlow<List<Label>> = labelRepository.getAllLabelsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf()
        )

    private val _selectedNotes: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    val selectedNotes = _selectedNotes.asStateFlow()

    private var getNotesJob: Job? = null

    init {
        val sorting = appSettingsRepository.getListSort()
        fetchNotes(sorting)
    }

    private fun fetchNotes(sorting: Sorting) = viewModelScope.launch {
        getNotesJob?.cancel()
        getNotesJob = noteRepository.getAllNotesStream()
            .onEach { notes ->

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
                    )
                }

            }
            .launchIn(viewModelScope)
    }

    fun deleteAllSelected() = viewModelScope.launch {
        _uiState.value.let { state ->
            if (state is NoteListUiState.Success) {
                noteRepository.deleteNotes(_selectedNotes.value)
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
        _selectedNotes.update { it.plus(note) }
    }

    fun unselectNote(note: Note) {
        _selectedNotes.update { it.minus(note) }
    }

    fun cancelNoteSelection() {
        _selectedNotes.update { listOf() }
    }

//    fun isSelection() = _selectedNotes.value.isNotEmpty()
//
//    fun selectedCount() = _selectedNotes.value.count()

}

sealed interface NoteListUiState {
    data class Success(
        val sorting: Sorting,
        val listStyle: ListStyle,
        val pinnedNotes: List<Note>,
        val notes: List<Note>
    ) : NoteListUiState
    object Loading : NoteListUiState
}