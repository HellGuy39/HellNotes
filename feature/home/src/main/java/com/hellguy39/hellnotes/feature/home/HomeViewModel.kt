package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.*
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.model.util.NoteSwipe
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.feature.home.util.DrawerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val noteRepository: NoteRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository,
    private val labelRepository: LabelRepository
): ViewModel() {

    private val drawerItem = MutableStateFlow(DrawerItem())

    private val selectedNotes = MutableStateFlow(listOf<Note>())

    private val lastActionNotes = MutableStateFlow(listOf<Note>())

    val uiState: StateFlow<HomeScreenUiState> =
        combine(
            dataStoreRepository.readListStyleState(),
            dataStoreRepository.readNoteStyleState(),
            dataStoreRepository.readNoteSwipesState(),
            selectedNotes
        ) { listStyle, noteStyle, noteSwipesState, selectedNotes ->
            HomeScreenUiState(
                listStyle = listStyle,
                noteStyle = noteStyle,
                noteSwipesState = noteSwipesState,
                selectedNotes = selectedNotes
            )
        }
            .stateIn(
                started = SharingStarted.WhileSubscribed(5_000),
                scope = viewModelScope,
                initialValue = HomeScreenUiState()
            )

    val drawerUiState: StateFlow<HomeScreenDrawerUiState> =
        combine(
            drawerItem,
            labelRepository.getAllLabelsStream()
        ) { drawerItem, labels ->
            HomeScreenDrawerUiState(
                drawerItem = drawerItem,
                drawerLabels = labels
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeScreenDrawerUiState()
            )

    fun updateListStyle() {
        viewModelScope.launch {
            val listStyle = uiState.value.listStyle
            dataStoreRepository.saveListStyleState(listStyle.swap())
        }
    }

    fun setDrawerItem(item: DrawerItem) {
        viewModelScope.launch {
            drawerItem.update { item }
        }
    }

    fun selectNote(note: Note) {
        viewModelScope.launch {
            selectedNotes.update { selectedNotes -> selectedNotes.plus(note) }
        }
    }

    fun unselectNote(note: Note) {
        viewModelScope.launch {
            selectedNotes.update { selectedNotes -> selectedNotes.minus(note) }
        }
    }

    fun cancelNoteSelection() {
        viewModelScope.launch {
            selectedNotes.update { listOf() }
        }
    }

    fun archiveSelectedNotes(isArchived: Boolean = true) {
        viewModelScope.launch {
            lastActionNotes.update { listOf() }
            selectedNotes.value.forEach { note -> archiveNote(note = note, isArchived = isArchived) }
            cancelNoteSelection()
        }
    }

    fun restoreSelectedNotesFromTrash() {
        viewModelScope.launch {
            selectedNotes.value.forEach { note ->
                trashRepository.deleteTrashByNote(note)
                noteRepository.insertNote(note)
            }
            cancelNoteSelection()
        }
    }

    fun deleteSelectedNotesFromTrash() {
        viewModelScope.launch {
            selectedNotes.value.forEach { note ->
                trashRepository.deleteTrashByNote(note)
            }
            cancelNoteSelection()
        }
    }

    fun deleteSelectedNotes() {
        viewModelScope.launch {
            lastActionNotes.update { listOf() }
            selectedNotes.value.forEach { note -> deleteNote(note = note, clearBuffer = false) }
            cancelNoteSelection()
        }
    }

    fun deleteNote(clearBuffer: Boolean = false, note: Note) {
        viewModelScope.launch {
            if (clearBuffer) { lastActionNotes.update { listOf() } }

            note.id?.let { id ->
                noteRepository.deleteNoteById(id)
                reminderRepository.deleteReminderByNoteId(id)
                labelRepository.deleteNoteIdFromLabels(id)
            }

            if (note.isNoteValid()) {
                trashRepository.insertTrash(
                    Trash(
                        note = note,
                        dateOfAdding = DateTimeUtils.getCurrentTimeInEpochMilli()
                    )
                )
            }

            lastActionNotes.update { notes -> notes.plus(note) }
        }
    }

    fun archiveNote(clearBuffer: Boolean = false, note: Note, isArchived: Boolean = true) {
        viewModelScope.launch {
            if (clearBuffer) { lastActionNotes.update { listOf() } }

            noteRepository.updateNote(note.copy(isArchived = isArchived))

            lastActionNotes.update { notes -> notes.plus(note) }
        }
    }

    fun restoreNoteFromTrash(note: Note) {
        viewModelScope.launch {
            trashRepository.deleteTrashByNote(note)
            noteRepository.insertNote(note)
        }
    }

    fun undoDeleteSelected() {
        viewModelScope.launch {
            lastActionNotes.value.let { notes ->
                notes.forEach { note ->
                    if (note.isNoteValid()) {
                        trashRepository.deleteTrashByNote(note)
                    }
                    noteRepository.insertNote(note)
                }
            }
            lastActionNotes.update { listOf() }
        }
    }

    fun undoArchiveSelected(isArchived: Boolean = false) {
        viewModelScope.launch {
            lastActionNotes.value.forEach { note ->
                archiveNote(clearBuffer = false, note = note, isArchived = !isArchived)
            }
            lastActionNotes.update { listOf() }
        }
    }

}

data class HomeScreenUiState(
    val noteSwipesState: NoteSwipesState = NoteSwipesState(false, NoteSwipe.None, NoteSwipe.None),
    val listStyle: ListStyle = ListStyle.Column,
    val noteStyle: NoteStyle = NoteStyle.Outlined,
    val selectedNotes: List<Note> = listOf()
)

data class HomeScreenDrawerUiState(
    val drawerLabels: List<Label> = listOf(),
    val drawerItem: DrawerItem = DrawerItem(),
)

