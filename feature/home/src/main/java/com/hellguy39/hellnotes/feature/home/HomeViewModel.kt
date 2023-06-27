package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.use_case.note.ArchiveNotesUseCase
import com.hellguy39.hellnotes.core.domain.use_case.trash.MoveNotesToTrashUseCase
import com.hellguy39.hellnotes.core.domain.use_case.trash.RestoreNoteFromTrashUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.ui.components.snack.SnackAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    labelRepository: LabelRepository,
    private val noteRepository: NoteRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val archiveNotesUseCase: ArchiveNotesUseCase,
    private val moveToTrashNotesUseCase: MoveNotesToTrashUseCase,
    private val restoreNoteFromTrashUseCase: RestoreNoteFromTrashUseCase
): ViewModel() {

    private val lastAction = MutableStateFlow(LastAction())

    private val _openedNoteId: MutableStateFlow<Long?> = MutableStateFlow(null)
    val openedNoteId = _openedNoteId.asStateFlow()

    val selectedNoteWrappers = MutableStateFlow(listOf<NoteWrapper>())

    val noteSwipesState = dataStoreRepository.readNoteSwipesState()
        .stateIn(
            initialValue = NoteSwipesState.initialInstance(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    val noteStyle = dataStoreRepository.readNoteStyleState()
        .stateIn(
            initialValue = NoteStyle.Outlined,
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    val listStyle = dataStoreRepository.readListStyleState()
        .stateIn(
            initialValue = ListStyle.Column,
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    val labels = labelRepository.getAllLabelsStream()
        .stateIn(
            initialValue = listOf(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    fun send(uiEvent: HomeUiEvent) {
        when(uiEvent) {
            is HomeUiEvent.CreateNewNote -> createNewNote()

            is HomeUiEvent.OpenNoteDetail -> openNoteDetail(uiEvent.noteId)

            is HomeUiEvent.ToggleListStyle -> toggleListStyle()

            is HomeUiEvent.ArchiveSelectedNotes -> archiveSelectedNotes(true)

            is HomeUiEvent.SelectNote -> selectNote(uiEvent.noteWrapper)

            is HomeUiEvent.CancelNoteSelection -> cancelNoteSelection()

            is HomeUiEvent.MoveToTrashSelectedNotes -> moteToTrashSelectedNotes()

            is HomeUiEvent.UnarchiveSelectedNotes -> archiveSelectedNotes(false)

            is HomeUiEvent.Undo -> undo()

            is HomeUiEvent.SwipeToArchiveNote -> swipeToArchive(uiEvent.noteWrapper)

            is HomeUiEvent.SwipeToDeleteNote -> swipeToDelete(uiEvent.noteWrapper)
        }
    }

    private fun createNewNote() {
        viewModelScope.launch {
            val newNoteId = noteRepository.insertNote(Note())
            openNoteDetail(newNoteId)
        }
    }

    private fun openNoteDetail(noteId: Long?) {
        viewModelScope.launch {
            _openedNoteId.update { noteId }
        }
    }

    private fun undo() {
        viewModelScope.launch {
            val action = lastAction.value
            val noteWrappers = action.noteWrappers

            when(action.snackAction) {
                is SnackAction.Delete -> restoreNoteFromTrashUseCase.invoke(noteWrappers)
                is SnackAction.Archive -> archiveNotesUseCase.invoke(noteWrappers, false)
                else -> Unit
            }
        }
    }

    private fun addLastAction(noteWrappers: List<NoteWrapper>, action: SnackAction) {
        viewModelScope.launch {
            lastAction.update {
                LastAction(
                    noteWrappers = noteWrappers,
                    snackAction = action
                )
            }
        }
    }

    private fun swipeToArchive(noteWrapper: NoteWrapper) {
        viewModelScope.launch {
            val noteWrappers = listOf(noteWrapper)
            addLastAction(noteWrappers = noteWrappers, action = SnackAction.Archive)
            archiveNotesUseCase.invoke(noteWrappers, true)
        }
    }

    private fun swipeToDelete(noteWrapper: NoteWrapper) {
        viewModelScope.launch {
            val noteWrappers = listOf(noteWrapper)
            addLastAction(noteWrappers = noteWrappers, action = SnackAction.Delete)
            moveToTrashNotesUseCase.invoke(noteWrappers)
        }
    }

    private fun toggleListStyle() {
        viewModelScope.launch {
            dataStoreRepository.saveListStyleState(listStyle.value.swap())
        }
    }

    private fun cancelNoteSelection() {
        viewModelScope.launch {
            selectedNoteWrappers.update { mutableListOf() }
        }
    }

    private fun archiveSelectedNotes(isArchive: Boolean) {
        viewModelScope.launch {
            val selectedNoteWrappers = selectedNoteWrappers.value
            addLastAction(selectedNoteWrappers, SnackAction.Archive)
            archiveNotesUseCase.invoke(selectedNoteWrappers, isArchive)
            cancelNoteSelection()
        }
    }

    private fun moteToTrashSelectedNotes() {
        viewModelScope.launch {
            val selectedNoteWrappers = selectedNoteWrappers.value
            addLastAction(selectedNoteWrappers, SnackAction.Delete)
            moveToTrashNotesUseCase.invoke(selectedNoteWrappers)
            cancelNoteSelection()
        }
    }


    private fun selectNote(noteWrapper: NoteWrapper) {
        viewModelScope.launch {
            selectedNoteWrappers.update { noteWrappers ->
                if (noteWrappers.contains(noteWrapper)) {
                    noteWrappers.toMutableList().apply {
                        remove(noteWrapper)
                    }
                } else {
                    noteWrappers.toMutableList().apply {
                        add(noteWrapper)
                    }
                }
            }
        }
    }
}

private data class LastAction(
    val noteWrappers: List<NoteWrapper> = listOf(),
    val snackAction: SnackAction = SnackAction.None,
)

sealed class HomeUiEvent {

    object CreateNewNote: HomeUiEvent()

    data class OpenNoteDetail(val noteId: Long?): HomeUiEvent()

    object ToggleListStyle: HomeUiEvent()

    object CancelNoteSelection: HomeUiEvent()

    object MoveToTrashSelectedNotes: HomeUiEvent()

    object ArchiveSelectedNotes: HomeUiEvent()

    object UnarchiveSelectedNotes: HomeUiEvent()

    data class SelectNote(val noteWrapper: NoteWrapper): HomeUiEvent()

    data class SwipeToDeleteNote(val noteWrapper: NoteWrapper): HomeUiEvent()

    data class SwipeToArchiveNote(val noteWrapper: NoteWrapper): HomeUiEvent()

    object Undo: HomeUiEvent()

}
