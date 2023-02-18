package com.hellguy39.hellnotes.feature.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.domain.system_features.AlarmScheduler
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val reminderRepository: ReminderRepository,
    private val labelRepository: LabelRepository,
    private val trashRepository: TrashRepository,
    savedStateHandle: SavedStateHandle,
    val alarmScheduler: AlarmScheduler,
    val dateHelper: DateHelper
): ViewModel() {

    private val noteViewModelState = MutableStateFlow(NoteDetailViewModelState(isLoading = true))

    val uiState = noteViewModelState
        .map(NoteDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            noteViewModelState.value.toUiState()
        )

    init {

        viewModelScope.launch {
            val noteId = savedStateHandle.get<Long>(ArgumentKeys.NoteId).let { id ->
                if(id != ArgumentDefaultValues.NewNote) {
                    id
                } else {
                    withContext(Dispatchers.IO) {
                        noteRepository.insertNote(Note())
                    }
                }
            } ?: return@launch

            launch {
                noteRepository.getNoteById(noteId).let { note ->
                    noteViewModelState.update { state ->
                        state.copy(note = note, isLoading = false)
                    }
                }
            }

            launch {
                labelRepository.getAllLabelsStream().collect { labels ->
                    noteViewModelState.update { state ->
                        state.copy(allLabels = labels)
                    }
                }
            }

            launch {
                reminderRepository.getRemindersByNoteIdStream(noteId).collect { reminders ->
                    noteViewModelState.update { state ->
                        state.copy(noteReminders = reminders)
                    }
                }
            }
        }
    }

    fun onUpdateNoteContent(text: String) = viewModelScope.launch {
        val currentTime = Calendar.getInstance().time.time

        noteViewModelState.update { state ->

            val updatedNote = state.note.copy(
                note = text,
                lastEditDate = currentTime
            )

            state.copy(
                note =  updatedNote
            )
        }

        saveNote()
    }

    fun onUpdateNoteTitle(text: String) = viewModelScope.launch {
        val currentTime = Calendar.getInstance().time.time

        noteViewModelState.update { state ->

            val updatedNote = state.note.copy(
                title = text,
                lastEditDate = currentTime
            )

            state.copy(
                note =  updatedNote
            )
        }

        saveNote()
    }

    fun insertRemind(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.insertReminder(reminder)
    }

    fun onUpdateIsPinned(isPinned: Boolean) = viewModelScope.launch {
        noteViewModelState.update { state ->
            state.copy(
                note = state.note.copy(isPinned = isPinned)
            )
        }
        saveNote()
    }

    fun onUpdateIsArchived(isArchived: Boolean) {
        viewModelScope.launch {
            noteViewModelState.update { state ->
                state.copy(
                    note = state.note.copy(isArchived = isArchived)
                )
            }
        }
        saveNote()
    }

    fun onDiscardNoteIfEmpty() = viewModelScope.launch {
        noteViewModelState.value.let { state ->
            val id = state.note.id
            if (id != null) {
                val reminds = reminderRepository.getRemindersByNoteId(id)
                if (!state.note.isNoteValid() && reminds.isEmpty()) {
                    onDeleteNote()
                }
            }
        }
    }

    fun onDeleteNote() = viewModelScope.launch {
        noteViewModelState.value.note.let { note ->
            note.id?.let { id ->
                noteRepository.deleteNoteById(id)
                reminderRepository.deleteReminderByNoteId(id)
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

    fun onDeleteLabel(label: Label) = viewModelScope.launch {
        label.id?.let { noteRepository.deleteLabelFromNotes(it) }
        labelRepository.deleteLabel(label)
    }

    fun selectLabel(label: Label) = viewModelScope.launch {

        noteViewModelState.update { state ->

            val updatedNote = state.note.copy(
                labelIds = state.note.labelIds.plus(label.id ?: return@launch)
            )

            state.copy(
                note =  updatedNote
            )
        }

        saveNote()
    }

    fun unselectLabel(label: Label) = viewModelScope.launch {
        noteViewModelState.update { state ->

            val updatedNote = state.note.copy(
                labelIds = state.note.labelIds.minus(label.id ?: return@launch)
            )

            state.copy(
                note =  updatedNote
            )
        }

        saveNote()
    }

    fun insertLabel(label: Label) = viewModelScope.launch {
        val labelId = labelRepository.insertLabel(label)
        selectLabel(labelRepository.getLabelById(labelId))
    }

    fun onUpdateLabelSearch(searchInput: String) {
        noteViewModelState.update {
            it.copy(
                labelSearch = searchInput
            )
        }
    }

    fun onUpdateRemind(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.updateReminder(reminder)
    }

    fun onDeleteRemind(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.deleteReminder(reminder)
    }

    private fun saveNote() = viewModelScope.launch {
        noteRepository.updateNote(
            noteViewModelState.value.note
        )
    }

}

private data class NoteDetailViewModelState(
    val note: Note = Note(),
    val allLabels: List<Label> = listOf(),
    val noteReminders: List<Reminder> = listOf(),
    val labelSearch: String = "",
    val isLoading: Boolean = true
) {
    fun toUiState() = NoteDetailUiState(
        note = note,
        noteLabels = allLabels.filter { note.labelIds.contains(it.id) },
        searchedLabels = allLabels.filter { it.name.contains(labelSearch) },
        noteReminders = noteReminders,
        labelSearch = labelSearch,
        isLoading = isLoading
    )
}

data class NoteDetailUiState(
    val note: Note,
    val isLoading: Boolean,
    val noteLabels: List<Label>,
    val searchedLabels: List<Label>,
    val noteReminders: List<Reminder>,
    val labelSearch: String
)