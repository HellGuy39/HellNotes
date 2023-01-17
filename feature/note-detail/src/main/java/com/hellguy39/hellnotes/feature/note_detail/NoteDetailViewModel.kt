package com.hellguy39.hellnotes.feature.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.system_features.AlarmScheduler
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.feature.note_detail.util.KEY_NOTE_ID
import com.hellguy39.hellnotes.feature.note_detail.util.NEW_NOTE_ID
import com.hellguy39.hellnotes.feature.note_detail.util.isNoteValid
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
    savedStateHandle: SavedStateHandle,
    val alarmScheduler: AlarmScheduler
): ViewModel() {

    private val noteViewModelState = MutableStateFlow(NoteDetailViewModelState())

    val uiState = noteViewModelState
        .map(NoteDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            noteViewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            val noteId = savedStateHandle.get<Long>(KEY_NOTE_ID).let { id ->
                if(id != NEW_NOTE_ID) {
                    id
                } else {
                    withContext(Dispatchers.Default) {
                        createNote()
                    }
                }
            } ?: return@launch

            launch {
                noteRepository.getNoteById(noteId).let { note ->
                    noteViewModelState.update { state ->
                        state.copy(note = note)
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
                reminderRepository.getRemindsByNoteIdStream(noteId).collect { reminders ->
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

    fun insertRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.insertRemind(remind)
    }

    fun onUpdateIsPinned(isPinned: Boolean) = viewModelScope.launch {

        noteViewModelState.update { state ->

            val updatedNote = state.note.copy(
                isPinned = isPinned
            )

            state.copy(
                note =  updatedNote
            )
        }

        saveNote()
    }

    fun onDiscardNoteIfEmpty() = viewModelScope.launch {
        noteViewModelState.value.let { state ->
            val id = state.note.id
            if (id != null) {
                val reminds = reminderRepository.getRemindsByNoteId(id)
                if (state.note.isNoteValid() && reminds.isEmpty()) {
                    onDeleteNote()
                }
            }
        }
    }

    fun onDeleteNote() = viewModelScope.launch {
        noteViewModelState.value.note.id?.let { noteId ->
            noteRepository.deleteNoteById(noteId)
            reminderRepository.deleteRemindByNoteId(noteId)
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

    fun onUpdateRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.updateRemind(remind)
    }

    fun onDeleteRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.deleteRemind(remind)
    }

    private fun saveNote() = viewModelScope.launch {
        noteRepository.updateNote(
            noteViewModelState.value.note
        )
    }

    private suspend fun createNote(): Long {
        return noteRepository.insertNote(Note())
    }

}

private data class NoteDetailViewModelState(
    val note: Note = Note(),
    val allLabels: List<Label> = listOf(),
    val noteReminders: List<Remind> = listOf(),
    val labelSearch: String = ""
) {
    fun toUiState() = NoteDetailUiState(
        note = note,
        noteLabels = allLabels.filter { note.labelIds.contains(it.id) },
        searchedLabels = allLabels.filter { it.name.contains(labelSearch) },
        noteReminders = noteReminders,
        labelSearch = labelSearch
    )
}

data class NoteDetailUiState(
    val note: Note,
    val noteLabels: List<Label>,
    val searchedLabels: List<Label>,
    val noteReminders: List<Remind>,
    val labelSearch: String
)