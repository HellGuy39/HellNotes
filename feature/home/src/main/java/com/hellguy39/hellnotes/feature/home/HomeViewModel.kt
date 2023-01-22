package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val appSettingsRepository: AppSettingsRepository
): ViewModel() {

    private val homeViewModelState = MutableStateFlow(HomeViewModelState())

    val noteListUiState = homeViewModelState
        .map(HomeViewModelState::toNoteListUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            homeViewModelState.value.toNoteListUiState()
        )

    val remindersUiState = homeViewModelState
        .map(HomeViewModelState::toRemindersUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            homeViewModelState.value.toRemindersUiState()
        )

    private val _selectedNotes: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    val selectedNotes = _selectedNotes.asStateFlow()

    init {
        viewModelScope.launch {

            homeViewModelState.update {
                it.copy(
                    sorting = appSettingsRepository.getListSort(),
                    listStyle = appSettingsRepository.getListStyle()
                )
            }

            launch {
                noteRepository.getAllNotesStream().collect { notes ->
                    homeViewModelState.update { it.copy(notes = notes) }
                }
            }
            launch {
                reminderRepository.getAllRemindsStream().collect { reminders ->
                    homeViewModelState.update { it.copy(reminders = reminders) }
                }
            }
            launch {
                labelRepository.getAllLabelsStream().collect { labels ->
                    homeViewModelState.update { it.copy(labels = labels) }
                }
            }
        }

    }

    fun deleteAllSelected() = viewModelScope.launch {
        noteRepository.deleteNotes(_selectedNotes.value)
    }

    fun updateListStyle() = viewModelScope.launch {

        val newStyle = homeViewModelState.value.listStyle.swap()
        appSettingsRepository.saveListStyle(newStyle)

        homeViewModelState.update { state ->
            state.copy(listStyle = newStyle)
        }
    }

    fun updateSorting(sorting: Sorting) = viewModelScope.launch {
        appSettingsRepository.saveListSort(sorting)
        homeViewModelState.update { state ->
            state.copy(sorting = sorting)
        }
    }

    fun selectNote(note: Note) { _selectedNotes.update { it.plus(note) } }

    fun unselectNote(note: Note) { _selectedNotes.update { it.minus(note) } }

    fun cancelNoteSelection() { _selectedNotes.update { listOf() } }

}

private data class HomeViewModelState(
    val sorting: Sorting = Sorting.DateOfCreation,
    val listStyle: ListStyle = ListStyle.Column,
    val reminders: List<Remind> = listOf(),
    val labels: List<Label> = listOf(),
    val notes: List<Note> = listOf()
) {
    fun toNoteListUiState(): HomeNoteListUiState {
        val sortedNotes = when(sorting) {
            is Sorting.DateOfCreation -> {
                notes.sortedByDescending { it.id }
            }
            is Sorting.DateOfLastEdit -> {
                notes.sortedByDescending { it.lastEditDate }
            }
        }.map { note ->
            note.toNoteDetailWrapper(
                reminders = reminders,
                labels = labels
            )
        }

        return HomeNoteListUiState(
            sorting = sorting,
            listStyle = listStyle,
            pinnedNotes = sortedNotes.filter { it.note.isPinned },
            unpinnedNotes = sortedNotes.filter { !it.note.isPinned }
        )
    }

    fun toRemindersUiState(): HomeRemindersUiState {
        return HomeRemindersUiState(
            listStyle = listStyle,
            notes = notes
                .map { note ->
                    note.toNoteDetailWrapper(
                        reminders = reminders.sortedBy { it.triggerDate },
                        labels = labels
                    )
                }
                .filter { wrapper ->
                    wrapper.reminders.isNotEmpty()
                }
                .sortedBy { wrapper ->
                    wrapper.reminders.first().triggerDate
                }
        )
    }
}

data class HomeNoteListUiState(
    val sorting: Sorting,
    val listStyle: ListStyle,
    val pinnedNotes: List<NoteDetailWrapper>,
    val unpinnedNotes: List<NoteDetailWrapper>
)

data class HomeRemindersUiState(
    val listStyle: ListStyle,
    val notes: List<NoteDetailWrapper>
)

private fun Note.toNoteDetailWrapper(
    reminders: List<Remind>,
    labels: List<Label>
) = NoteDetailWrapper(
    note = this,
    labels = labels.filter { label -> labelIds.contains(label.id)  },
    reminders = reminders.filter { reminder -> id == reminder.noteId }
)