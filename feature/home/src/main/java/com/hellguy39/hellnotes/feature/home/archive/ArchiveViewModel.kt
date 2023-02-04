package com.hellguy39.hellnotes.feature.home.archive

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
class ArchiveViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository,
    private val dateHelper: DateHelper
): ViewModel() {

    private val archivedViewModelState = MutableStateFlow(ArchiveViewModelState())

    init {
        viewModelScope.launch {
            launch {
                noteRepository.getAllNotesStream().collect { notes ->
                    archivedViewModelState.update { it.copy(notes = notes) }
                }
            }
            launch {
                reminderRepository.getAllRemindsStream().collect { reminders ->
                    archivedViewModelState.update { it.copy(reminders = reminders) }
                }
            }
            launch {
                labelRepository.getAllLabelsStream().collect { labels ->
                    archivedViewModelState.update { it.copy(labels = labels) }
                }
            }
        }
    }

    val uiState = archivedViewModelState
        .map(ArchiveViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            archivedViewModelState.value.toUiState()
        )

    fun selectNote(note: Note) = viewModelScope.launch {
        archivedViewModelState.update {
            it.copy(selectedNotes = it.selectedNotes.plus(note))
        }
    }

    fun unselectNote(note: Note) = viewModelScope.launch {
        archivedViewModelState.update {
            it.copy(selectedNotes = it.selectedNotes.minus(note))
        }
    }

    fun cancelNoteSelection() = viewModelScope.launch {
        archivedViewModelState.update {
            it.copy(selectedNotes = listOf())
        }
    }

    fun deleteAllSelected() {
        viewModelScope.launch {
            archivedViewModelState.value.selectedNotes.let { notes ->

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
                archivedViewModelState.value.notes.map {
                    it.copy(
                        isArchived = false
                    )
                }
            )

            archivedViewModelState.update { it.copy(selectedNotes = listOf()) }
        }
    }

}

private data class ArchiveViewModelState(
    val reminders: List<Remind> = listOf(),
    val labels: List<Label> = listOf(),
    val notes: List<Note> = listOf(),
    val selectedNotes: List<Note> = listOf()
) {
    fun toUiState(): ArchiveUiState = ArchiveUiState(
            notes = notes.filter { it.isArchived }
                .map { note ->
                    note.toNoteDetailWrapper(
                        reminders = reminders,
                        labels = labels
                    )
                },
            selectedNotes = selectedNotes
        )
}

data class ArchiveUiState(
    val notes: List<NoteDetailWrapper>,
    val selectedNotes: List<Note>
)
