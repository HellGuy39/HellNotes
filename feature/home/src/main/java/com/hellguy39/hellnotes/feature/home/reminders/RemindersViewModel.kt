package com.hellguy39.hellnotes.feature.home.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
): ViewModel() {

    private val remindersViewModelState = MutableStateFlow(RemindersViewModelState())

    val uiState = remindersViewModelState
        .map(RemindersViewModelState::toRemindersUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            remindersViewModelState.value.toRemindersUiState()
        )

    init {
        viewModelScope.launch {

            launch {
                noteRepository.getAllNotesStream().collect { notes ->
                    remindersViewModelState.update { it.copy(notes = notes) }
                }
            }
            launch {
                reminderRepository.getAllRemindersStream().collect { reminders ->
                    remindersViewModelState.update { it.copy(reminders = reminders) }
                }
            }
            launch {
                labelRepository.getAllLabelsStream().collect { labels ->
                    remindersViewModelState.update { it.copy(labels = labels) }
                }
            }
        }
    }

    fun deleteAllSelected() = viewModelScope.launch {
        noteRepository.deleteNotes(remindersViewModelState.value.selectedNotes)
        cancelNoteSelection()
    }

    fun selectNote(note: Note) = viewModelScope.launch {
        remindersViewModelState.update {
            it.copy(
                selectedNotes = it.selectedNotes.plus(note)
            )
        }
    }

    fun unselectNote(note: Note) = viewModelScope.launch {
        remindersViewModelState.update {
            it.copy(
                selectedNotes = it.selectedNotes.minus(note)
            )
        }
    }

    fun cancelNoteSelection() = viewModelScope.launch {
        remindersViewModelState.update {
            it.copy(
                selectedNotes = listOf()
            )
        }
    }

}

private data class RemindersViewModelState(
    val reminders: List<Reminder> = listOf(),
    val labels: List<Label> = listOf(),
    val notes: List<Note> = listOf(),
    val selectedNotes: List<Note> = listOf()
) {
    fun toRemindersUiState(): RemindersUiState {
        return RemindersUiState(
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
                },
            selectedNotes = selectedNotes
        )
    }
}

data class RemindersUiState(
    val notes: List<NoteDetailWrapper>,
    val selectedNotes: List<Note>
)
