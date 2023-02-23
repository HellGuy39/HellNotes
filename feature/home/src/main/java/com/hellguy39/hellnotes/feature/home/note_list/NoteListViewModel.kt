package com.hellguy39.hellnotes.feature.home.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.*
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.util.Sorting
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository,
    private val dataStoreRepository: DataStoreRepository,
): ViewModel() {

    private val noteListViewModelState = MutableStateFlow(NoteListViewModelState(isLoading = true))

    private val lastDeletedNotes = MutableStateFlow(listOf<Note>())

    val uiState = noteListViewModelState
        .map(NoteListViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            noteListViewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {

            launch {
                dataStoreRepository.readListSortState().collect { sorting ->
                    noteListViewModelState.update {
                        it.copy(
                            sorting = sorting,
                        )
                    }
                }
            }

            launch {
                noteRepository.getAllNotesStream().collect { notes ->
                    noteListViewModelState.update {
                        it.copy(
                            notes = notes,
                            isLoading = false
                        )
                    }
                }
            }
            launch {
                reminderRepository.getAllRemindersStream().collect { reminders ->
                    noteListViewModelState.update { it.copy(reminders = reminders) }
                }
            }
            launch {
                labelRepository.getAllLabelsStream().collect { labels ->
                    noteListViewModelState.update { it.copy(labels = labels) }
                }
            }
        }

    }
    fun deleteAllSelected() = viewModelScope.launch {
        noteListViewModelState.value.selectedNotes.let { notes ->

            lastDeletedNotes.update { notes }

            notes.forEach { note ->
                note.id?.let { id ->
                    noteRepository.deleteNoteById(id)
                    reminderRepository.deleteReminderByNoteId(id)
                }
                if (note.isNoteValid()) {
                    trashRepository.insertTrash(
                        Trash(
                            note = note.copy(labelIds = listOf()),
                            dateOfAdding = DateTimeUtils.getCurrentTimeInEpochMilli()
                        )
                    )
                }
            }
        }

        cancelNoteSelection()
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            lastDeletedNotes.update { listOf(note) }

            note.id?.let { id ->
                noteRepository.deleteNoteById(id)
                reminderRepository.deleteReminderByNoteId(id)
            }
            if (note.isNoteValid()) {
                trashRepository.insertTrash(
                    Trash(
                        note = note.copy(labelIds = listOf()),
                        dateOfAdding = DateTimeUtils.getCurrentTimeInEpochMilli()
                    )
                )
            }
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            lastDeletedNotes.value.let { notes ->
                notes.forEach { note ->
                    if (note.isNoteValid()) {
                        trashRepository.deleteTrashByNote(note)
                    }
                    noteRepository.insertNote(note)
                }
            }
            lastDeletedNotes.update { listOf() }
        }
    }

    fun updateSorting(sorting: Sorting) {
        viewModelScope.launch {
            dataStoreRepository.saveListSortState(sorting)
            noteListViewModelState.update { state ->
                state.copy(sorting = sorting)
            }
        }
    }

    fun selectNote(note: Note) = viewModelScope.launch {
        noteListViewModelState.update {
            it.copy(
                selectedNotes = it.selectedNotes.plus(note)
            )
        }
    }

    fun unselectNote(note: Note) = viewModelScope.launch {
        noteListViewModelState.update {
            it.copy(
                selectedNotes = it.selectedNotes.minus(note)
            )
        }
    }

    fun cancelNoteSelection() = viewModelScope.launch {
        noteListViewModelState.update {
            it.copy(
                selectedNotes = listOf()
            )
        }
    }

    fun archiveAllSelected() {
        viewModelScope.launch {
            noteRepository.updateNotes(
                noteListViewModelState.value.selectedNotes.map {
                    it.copy(
                        isArchived = true
                    )
                }
            )

            noteListViewModelState.update { it.copy(selectedNotes = listOf()) }
        }
    }

}

private data class NoteListViewModelState(
    val sorting: Sorting = Sorting.DateOfCreation,
    val isLoading: Boolean = true,
    val reminders: List<Reminder> = listOf(),
    val labels: List<Label> = listOf(),
    val notes: List<Note> = listOf(),
    val selectedNotes: List<Note> = listOf()
) {
    fun toUiState(): NoteListUiState {

        val sortedNotes = when(sorting) {
            is Sorting.DateOfCreation -> {
                notes.sortedByDescending { it.id }
            }
            is Sorting.DateOfLastEdit -> {
                notes.sortedByDescending { it.editedAt }
            }
        }
        .filter { !it.isArchived }
        .map { note ->
            note.toNoteDetailWrapper(
                reminders = reminders,
                labels = labels
            )
        }

        return NoteListUiState(
            sorting = sorting,
            pinnedNotes = sortedNotes.filter { it.note.isPinned },
            unpinnedNotes = sortedNotes.filter { !it.note.isPinned },
            selectedNotes = selectedNotes,
            isLoading = isLoading
        )
    }
}

data class NoteListUiState(
    val sorting: Sorting,
    val isLoading: Boolean,
    val pinnedNotes: List<NoteDetailWrapper>,
    val unpinnedNotes: List<NoteDetailWrapper>,
    val selectedNotes: List<Note>
)
