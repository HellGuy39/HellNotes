package com.hellguy39.hellnotes.feature.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
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
): ViewModel() {

    private val noteViewModelState = MutableStateFlow(NoteDetailViewModelState(isLoading = true))

    val uiState = noteViewModelState
        .map(NoteDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
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
                        state.copy(noteLabels = labels.filter { label ->
                                label.noteIds.contains(noteId)
                            }
                        )
                    }
                }
            }

            launch {
                reminderRepository.getRemindersByNoteIdStream(noteId).collect { reminders ->
                    noteViewModelState.update { state -> state.copy(noteReminders = reminders) }
                }
            }
        }
    }

    fun onUpdateNoteContent(text: String) {
        viewModelScope.launch {
            noteViewModelState.update { state ->
                state.copy(
                    note = state.note.copy(
                        note = text,
                        editedAt = DateTimeUtils.getCurrentTimeInEpochMilli()
                    )
                )
            }

            saveNote()
        }
    }

    fun onUpdateNoteTitle(text: String) = viewModelScope.launch {
        val currentTime = Calendar.getInstance().time.time

        noteViewModelState.update { state ->

            val updatedNote = state.note.copy(
                title = text,
                editedAt = currentTime
            )

            state.copy(
                note =  updatedNote
            )
        }

        saveNote()
    }

    fun onUpdateIsPinned(isPinned: Boolean) = viewModelScope.launch {
        noteViewModelState.update { state ->
            state.copy(note = state.note.copy(isPinned = isPinned))
        }
        saveNote()
    }

    fun onUpdateIsArchived(isArchived: Boolean) {
        viewModelScope.launch {
            noteViewModelState.update { state ->
                state.copy(note = state.note.copy(isArchived = isArchived))
            }
        }
        saveNote()
    }

    fun onDiscardNoteIfEmpty() = viewModelScope.launch {
        noteViewModelState.value.let { state ->
            val id = state.note.id
            if (id != null) {
                val reminds = reminderRepository.getRemindersByNoteId(id)
                if (!state.note.isNoteValid() && reminds.isEmpty() && state.noteLabels.isEmpty()) {
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
                        note = note,
                        dateOfAdding = DateTimeUtils.getCurrentTimeInEpochMilli()
                    )
                )
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note = noteViewModelState.value.note)
        }
    }

    fun onAddChecklistItem() {
        viewModelScope.launch {
            noteViewModelState.update { state ->
                state.copy(
                    note = state.note.copy(
                        checklist = state.note.checklist.plus(
                            CheckItem.newInstance(position = state.note.checklist.size + 1)
                        )
                    )
                )
            }
            saveNote()
        }
    }

    fun onUpdateChecklistItemText(item: CheckItem, text: String) {
        viewModelScope.launch {
            val checklist = noteViewModelState.value.note.checklist.toMutableList()

            val index = checklist.indexOf(item)
            checklist[index] = checklist[index].copy(text = text)

            noteViewModelState.update { state ->
                state.copy(
                    note = state.note.copy(checklist = checklist)
                )
            }

            saveNote()
        }
    }

    fun onUpdateChecklistItemChecked(item: CheckItem, isChecked: Boolean) {
        viewModelScope.launch {
            val checklist = noteViewModelState.value.note.checklist.toMutableList()
            val index = checklist.indexOf(item)

            checklist[index] = checklist[index].copy(isChecked = isChecked)

            noteViewModelState.update { state ->
                state.copy(
                    note = state.note.copy(checklist = checklist)
                )
            }

            saveNote()
        }
    }

    fun onRemoveChecklistItem(item: CheckItem) {
        viewModelScope.launch {
            val checklist = noteViewModelState.value.note.checklist.toMutableList()
            checklist.remove(item)

            noteViewModelState.update { state ->
                state.copy(
                    note = state.note.copy(checklist = checklist)
                )
            }
            saveNote()
        }
    }

    fun onMoveChecklistItem(fromIndex: Int, toIndex: Int) {
        viewModelScope.launch {
            val checklist = noteViewModelState.value.note.checklist.toMutableList()
            checklist.apply {
                add(toIndex, removeAt(fromIndex))
            }
            noteViewModelState.update { state ->
                state.copy(
                    note = state.note.copy(checklist = checklist)
                )
            }

            saveNote()
        }
    }

}

private data class NoteDetailViewModelState(
    val note: Note = Note(),
    val noteLabels: List<Label> = listOf(),
    val noteReminders: List<Reminder> = listOf(),
    val isLoading: Boolean = true
) {
    fun toUiState() = NoteDetailUiState(
        note = note,
        noteLabels = noteLabels,
        noteReminders = noteReminders,
        isLoading = isLoading
    )
}

data class NoteDetailUiState(
    val note: Note,
    val isLoading: Boolean,
    val noteLabels: List<Label>,
    val noteReminders: List<Reminder>,
)