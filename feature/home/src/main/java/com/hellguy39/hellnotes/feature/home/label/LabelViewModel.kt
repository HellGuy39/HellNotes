package com.hellguy39.hellnotes.feature.home.label

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
class LabelViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
): ViewModel() {

    private val labelViewModelState = MutableStateFlow(LabelViewModelState())

    val uiState = labelViewModelState
        .map(LabelViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            labelViewModelState.value.toUiState()
        )

    fun selectLabel(label: Label) {
        viewModelScope.launch {
            labelViewModelState.update {
                it.copy(selectedLabel = label)
            }
        }
    }

    init {
        viewModelScope.launch {

            launch {
                noteRepository.getAllNotesStream().collect { notes ->
                    labelViewModelState.update {
                        it.copy(
                            notes = notes,
                        )
                    }
                }
            }
            launch {
                reminderRepository.getAllRemindsStream().collect { reminders ->
                    labelViewModelState.update { it.copy(reminders = reminders) }
                }
            }
            launch {
                labelRepository.getAllLabelsStream().collect { labels ->
                    labelViewModelState.update { it.copy(labels = labels) }
                }
            }
        }
    }

}

private data class LabelViewModelState(
    val selectedLabel: Label = Label(),
    val reminders: List<Remind> = listOf(),
    val labels: List<Label> = listOf(),
    val notes: List<Note> = listOf(),
    val selectedNotes: List<Note> = listOf()
) {
    fun toUiState() = LabelUiState(
        notes = notes
            .filter { !it.isArchived }
            .map { note ->
                note.toNoteDetailWrapper(
                    reminders = reminders,
                    labels = labels
                )
            }
            .filter { it.labels.contains(selectedLabel) },
        selectedNotes = selectedNotes,
        label = selectedLabel
    )
}

data class LabelUiState(
    val label: Label,
    val notes: List<NoteDetailWrapper>,
    val selectedNotes: List<Note>,
)