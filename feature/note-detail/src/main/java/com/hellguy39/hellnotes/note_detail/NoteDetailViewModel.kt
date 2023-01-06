package com.hellguy39.hellnotes.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.domain.repository.LabelRepository
import com.hellguy39.hellnotes.domain.repository.NoteRepository
import com.hellguy39.hellnotes.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.domain.AlarmEvents
import com.hellguy39.hellnotes.domain.note.IsNoteValidUseCase
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.util.KEY_NOTE_ID
import com.hellguy39.hellnotes.note_detail.util.NEW_NOTE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val isNoteValidUseCase: IsNoteValidUseCase,
    private val reminderRepository: ReminderRepository,
    private val labelRepository: LabelRepository,
    savedStateHandle: SavedStateHandle,
    val alarmEvents: AlarmEvents
): ViewModel() {

    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())
    val note = _note.asStateFlow()

    private val _noteReminds: MutableStateFlow<List<Remind>> = MutableStateFlow(listOf())
    val noteReminds = _noteReminds.asStateFlow()

    private val _noteLabels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
    val noteLabels = _noteLabels.asStateFlow()

    private val _allLabels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
    val allLabels = _allLabels.asStateFlow()

    private var getLabelsJob: Job? = null
    private var getNoteRemindersJob: Job? = null

    init {
        savedStateHandle.get<Long>(KEY_NOTE_ID)?.let { id ->
            if(id != NEW_NOTE_ID) {
                fetchNote(id)
                fetchNoteReminds(id)
            } else {
                createNote { noteId ->
                    fetchNote(noteId)
                    fetchNoteReminds(noteId)
                }
            }
            fetchLabels()
        }
    }

    private fun fetchLabels(query: String = "") = viewModelScope.launch {
        getLabelsJob?.cancel()
        getLabelsJob = labelRepository.getAllLabelsStream(query)
            .onEach { labels ->
                _allLabels.update { labels }
            }
            .launchIn(viewModelScope)
    }

    fun updateNoteContent(text: String) {
        val currentTime = Calendar.getInstance().time.time
        _note.update {
            it.copy(
                note = text,
                lastEditDate = currentTime
            )
        }
        saveNote()
    }

    fun insertRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.insertRemind(remind)
        //_noteReminds.update { it.plus(remind) }
    }

    private fun fetchNoteReminds(noteId: Long) = viewModelScope.launch {
        getNoteRemindersJob?.cancel()
        getNoteRemindersJob = reminderRepository.getRemindsByNoteIdStream(noteId)
            .onEach { reminders ->
                _noteReminds.update { reminders }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchNoteLabels(labelIds: List<Long>) = viewModelScope.launch {
        val labels = mutableListOf<Label>()
        labelIds.forEach { id ->
            labels.add(labelRepository.getLabelById(id))
        }
        _noteLabels.update { labels }
    }

    fun updateNoteTitle(text: String) {
        val currentTime = Calendar.getInstance().time.time
        _note.update {
            it.copy(
                title = text,
                lastEditDate = currentTime
            )
        }
        saveNote()
    }

    fun updateNoteBackground(newColor: Long) = viewModelScope.launch {
        _note.update { it.copy(colorHex = newColor) }
        saveNote()
    }

    fun updateIsPinned(isPinned: Boolean) = viewModelScope.launch {
        _note.update { it.copy(isPinned = isPinned) }
        saveNote()
    }

    private fun fetchNote(id: Long) = viewModelScope.launch {
        val fetchedNote = noteRepository.getNoteById(id)
        _note.update { fetchedNote }
        fetchNoteLabels(fetchedNote.labelIds)
    }

    private fun saveNote() = viewModelScope.launch {
        _note.value.let { noteRepository.updateNote(it) }
    }

    private fun createNote(
        onNoteCreated: (noteId: Long) -> Unit
    ) = viewModelScope.launch {
        val noteId = noteRepository.insertNote(Note())
        onNoteCreated(noteId)
    }

    fun discardNoteIfEmpty() = viewModelScope.launch {
        _note.value.let {
            val id = it.id
            if (id != null) {
                val reminds = reminderRepository.getRemindsByNoteId(id)
                if (!isNoteValidUseCase.invoke(it) && reminds.isEmpty()) {
                    deleteNote()
                }
            }
        }
    }

    fun deleteNote() = viewModelScope.launch {
        _note.value.id?.let {
            noteRepository.deleteNoteById(it)
            reminderRepository.deleteRemindByNoteId(it)
        }
    }

    fun selectLabel(label: Label) = viewModelScope.launch {
        label.id?.let { id ->
            _note.update { it.copy(labelIds = it.labelIds.plus(id)) }
        }
        _noteLabels.update { it.plus(label) }
        saveNote()
    }

    fun unselectLabel(label: Label) = viewModelScope.launch {
        label.id?.let { id ->
            _note.update { it.copy(labelIds = it.labelIds.minus(id)) }
        }
        _noteLabels.update { it.minus(label) }
        saveNote()
    }

    fun insertLabel(label: Label) = viewModelScope.launch {
        labelRepository.insertLabel(label)
    }

    fun updateLabelQuery(query: String) = viewModelScope.launch {
        fetchLabels(query)
    }

    fun updateRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.updateRemind(remind)
    }

    fun deleteRemind(remind: Remind) = viewModelScope.launch {
        reminderRepository.deleteRemind(remind)
    }

}