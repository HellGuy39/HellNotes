package com.hellguy39.hellnotes.feature.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.*
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
    private val checklistRepository: ChecklistRepository,
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
                checklistRepository.getAllChecklistsStream().collect { checklists ->
                    noteViewModelState.update { state ->
                        state.copy(
                            checklists = checklists
                                .filter { checklists -> checklists.noteId == noteId }
                        )
                    }
                }
            }

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
                        state.copy(
                            noteLabels = labels.filter { label ->
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
                val checklists = checklistRepository.getChecklistsByNoteId(id)
                if (!state.note.isNoteValid() && reminds.isEmpty() && state.noteLabels.isEmpty() && checklists.isEmpty()) {
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
                checklistRepository.deleteChecklistByNoteId(id)
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

    fun onAddChecklistItem(checklist: Checklist) {
        viewModelScope.launch {

            val items = checklist.items.toMutableList().apply {
                add(ChecklistItem.newInstance())
            }

            noteViewModelState.update { state ->
                state.copy(
                    checklists = state.checklists.toMutableList().apply {
                        this[indexOf(checklist)] = checklist.copy(items = items)
                    }
                )
            }

            saveChecklists()
        }
    }

    fun onDeleteChecklist(checklist: Checklist) {
        viewModelScope.launch {
            checklistRepository.deleteChecklistById(checklist.id ?: return@launch)
        }
    }

    fun onDeleteChecklistItem(checklist: Checklist, item: ChecklistItem) {
        viewModelScope.launch {
            val items = checklist.items.toMutableList().apply {
                remove(item)
            }

            noteViewModelState.update { state ->
                state.copy(
                    checklists = state.checklists.toMutableList().apply {
                        this[indexOf(checklist)] = checklist.copy(items = items)
                    }
                )
            }

            saveChecklists()
        }
    }

    private fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note = noteViewModelState.value.note)
        }
    }

    private fun saveChecklists() {
        viewModelScope.launch {
            val checklists = noteViewModelState.value.checklists
            checklistRepository.updateChecklists(checklists)
        }
    }

    fun onAddChecklist() {
        viewModelScope.launch {
            val noteId = noteViewModelState.value.note.id ?: return@launch
            checklistRepository.insertChecklist(
                checklist = Checklist.initialInstance(
                    noteId = noteId, items = listOf(ChecklistItem.newInstance())
                )
            )
        }
    }

    fun onUpdateChecklistName(
        checklist: Checklist,
        name: String
    ) {
        viewModelScope.launch {
            noteViewModelState.update { state ->
                state.copy(
                    checklists = state.checklists.toMutableList().apply {
                        this[indexOf(checklist)] = checklist.copy(name = name)
                    }
                )
            }
            saveChecklists()
        }
    }

    fun onUpdateChecklistItemText(
        checklist: Checklist,
        checklistItem: ChecklistItem,
        text: String
    ) {
        viewModelScope.launch {
            noteViewModelState.update { state ->
                state.copy(
                    checklists = state.checklists.toMutableList().apply {
                        this[indexOf(checklist)] = checklist.copy(
                            items = checklist.items.toMutableList().apply {
                                this[indexOf(checklistItem)] = checklistItem.copy(text = text)
                            }
                        )
                    }
                )
            }
            saveChecklists()
        }
    }

    fun onUpdateChecklistItemChecked(
        checklist: Checklist,
        item: ChecklistItem,
        isChecked: Boolean
    ) {
        viewModelScope.launch {
            val index = checklist.items.indexOf(item)

            val items = checklist.items.toMutableList().apply {
                this[index] = this[index].copy(isChecked = isChecked)
            }

            noteViewModelState.update { state ->
                state.copy(
                    checklists = state.checklists.toMutableList().apply {
                        this[indexOf(checklist)] = checklist.copy(items = items)
                    }
                )
            }

            saveChecklists()
        }
    }

    fun onCheckAllItems(checklist: Checklist) {
        viewModelScope.launch {

            val items = checklist.items.map { item ->
                item.copy(isChecked = true)
            }

            noteViewModelState.update { state ->
                state.copy(
                    checklists = state.checklists.toMutableList().apply {
                        this[indexOf(checklist)] = checklist.copy(items = items)
                    }
                )
            }

            saveChecklists()
        }
    }

    fun onUncheckAllItems(checklist: Checklist) {
        viewModelScope.launch {

            val items = checklist.items.map { item ->
                item.copy(isChecked = false)
            }

            noteViewModelState.update { state ->
                state.copy(
                    checklists = state.checklists.toMutableList().apply {
                        this[indexOf(checklist)] = checklist.copy(items = items)
                    }
                )
            }

            saveChecklists()
        }
    }


}

private data class NoteDetailViewModelState(
    val note: Note = Note(),
    val noteLabels: List<Label> = listOf(),
    val noteReminders: List<Reminder> = listOf(),
    val checklists: List<Checklist> = listOf(),
    val isLoading: Boolean = true
) {
    fun toUiState() = NoteDetailUiState(
        note = note,
        noteLabels = noteLabels,
        noteReminders = noteReminders,
        isLoading = isLoading,
        checklists = checklists
    )
}

data class NoteDetailUiState(
    val note: Note,
    val isLoading: Boolean,
    val noteLabels: List<Label>,
    val noteReminders: List<Reminder>,
    val checklists: List<Checklist>
)