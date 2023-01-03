package com.hellguy39.hellnotes.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.data.repository.LabelRepository
import com.hellguy39.hellnotes.data.repository.NoteRepository
import com.hellguy39.hellnotes.data.repository.RemindRepository
import com.hellguy39.hellnotes.domain.note.IsNoteValidUseCase
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.model.util.Sorting
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
    private val remindRepository: RemindRepository,
    private val labelRepository: LabelRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _note: MutableStateFlow<Note> = MutableStateFlow(Note())
    val note = _note.asStateFlow()

    private val _reminds: MutableStateFlow<List<Remind>> = MutableStateFlow(listOf())
    val reminds = _reminds.asStateFlow()

    private val _labels: MutableStateFlow<List<Label>> = MutableStateFlow(listOf())
    val labels = _labels.asStateFlow()

    private var getLabelsJob: Job? = null

    init {
        savedStateHandle.get<Long>(KEY_NOTE_ID)?.let { id ->
            if(id != NEW_NOTE_ID) {
                fetchNote(id)
                fetchReminds(id)
            } else {
                createAndFetchNote()
            }
            fetchLabels()
        }
    }

    private fun fetchLabels(query: String = "") = viewModelScope.launch {
        getLabelsJob?.cancel()
        getLabelsJob = labelRepository.getAllLabelsStream(query)
            .onEach { labels ->
                _labels.update { labels }
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
        remindRepository.insertRemind(remind)
        _reminds.update { it.plus(remind) }
    }

    private fun fetchReminds(noteId: Long) = viewModelScope.launch {
        val reminds = remindRepository.getRemindsByNoteId(noteId)
        _reminds.update { reminds }
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
    }

    private fun saveNote() = viewModelScope.launch {
        _note.value.let { noteRepository.updateNote(it) }
    }

    private fun createAndFetchNote() = viewModelScope.launch {
        val noteId = noteRepository.insertNote(Note())
        fetchNote(noteId)
    }

    fun discardNoteIfEmpty() = viewModelScope.launch {
        _note.value.let {
            val id = it.id
            if (id != null) {
                val reminds = remindRepository.getRemindsByNoteId(id)
                if (!isNoteValidUseCase.invoke(it) && reminds.isEmpty()) {
                    deleteNote()
                }
            }
        }
    }

    fun deleteNote() = viewModelScope.launch {
        _note.value.id?.let {
            noteRepository.deleteNoteById(it)
            remindRepository.deleteRemindByNoteId(it)
        }
    }

    fun selectLabel(labelId: Long) = viewModelScope.launch {
        _note.update { it.copy(labelIds = it.labelIds.plus(labelId)) }
        saveNote()
    }

    fun unselectLabel(labelId: Long) = viewModelScope.launch {
        _note.update { it.copy(labelIds = it.labelIds.minus(labelId)) }
        saveNote()
    }

    fun insertLabel(label: Label) = viewModelScope.launch {
        labelRepository.insertLabel(label)
    }

    fun updateLabelQuery(query: String) = viewModelScope.launch {
        fetchLabels(query)
    }

}