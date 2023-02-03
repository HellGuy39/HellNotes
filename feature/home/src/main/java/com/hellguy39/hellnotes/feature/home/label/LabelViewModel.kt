package com.hellguy39.hellnotes.feature.home.label

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.ui.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository,
    private val dateHelper: DateHelper
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

    fun selectNote(note: Note) = viewModelScope.launch {
        labelViewModelState.update {
            it.copy(selectedNotes = it.selectedNotes.plus(note))
        }
    }

    fun unselectNote(note: Note) = viewModelScope.launch {
        labelViewModelState.update {
            it.copy(selectedNotes = it.selectedNotes.minus(note))
        }
    }

    fun cancelNoteSelection() = viewModelScope.launch {
        labelViewModelState.update {
            it.copy(selectedNotes = listOf())
        }
    }

    fun deleteAllSelected() {
        viewModelScope.launch {
            labelViewModelState.value.selectedNotes.let { notes ->

                notes.forEach { note ->
                    note.id?.let { id ->
                        noteRepository.deleteNoteById(id)
                        reminderRepository.deleteRemindByNoteId(id)
                    }
                    if (note.isNoteValid()) {
                        trashRepository.insertTrash(
                            Trash(
                                note = note.copy(labelIds = listOf()),
                                dateOfAdding = dateHelper.getCurrentTimeInEpochMilli()
                            )
                        )
                    }
                }
            }

            cancelNoteSelection()
        }
    }

    fun archiveAllSelected() {
        viewModelScope.launch {
            noteRepository.updateNotes(
                labelViewModelState.value.selectedNotes.map {
                    it.copy(
                        isArchived = true
                    )
                }
            )

            labelViewModelState.update { it.copy(selectedNotes = listOf()) }
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