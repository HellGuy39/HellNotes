package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.MoveNoteToTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.RestoreNoteFromTrashUseCase
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionViewModel
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val trashRepository: TrashRepository,
        private val moveNoteToTrashUseCase: MoveNoteToTrashUseCase,
        private val restoreNoteFromTrashUseCase: RestoreNoteFromTrashUseCase,
    ) : ViewModel() {
        private val lastNoteAction = MutableStateFlow(NoteAction())

        private val _selectedNotes = MutableStateFlow(listOf<Note>())
        val selectedNotes: StateFlow<List<Note>> = _selectedNotes.asStateFlow()

        private val _actionSingleEvents = Channel<ActionSingleEvent>()
        val actionSingleEvents = _actionSingleEvents.receiveAsFlow()

        fun selectNote(note: Note) {
            viewModelScope.launch {
                _selectedNotes.update { selectedNotes -> selectedNotes.plus(note) }
            }
        }

        fun unselectNote(note: Note) {
            viewModelScope.launch {
                _selectedNotes.update { selectedNotes -> selectedNotes.minus(note) }
            }
        }

        fun cancelNoteSelection() {
            viewModelScope.launch {
                _selectedNotes.update { listOf() }
            }
        }

        fun restoreNoteFromTrash(note: Note) {
            viewModelScope.launch {
                restoreNoteFromTrashUseCase.invoke(note)
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
            delete(*_selectedNotes.value.toTypedArray())
        }

        fun deleteNote(note: Note) {
            delete(note)
        }

        private fun delete(vararg notes: Note) {
            viewModelScope.launch {
                notes.forEach { note ->
                    moveNoteToTrashUseCase.invoke(note)
                }

                lastNoteAction.update {
                    NoteAction(
                        Action.Delete,
                        notes.asList(),
                    )
                }

                _actionSingleEvents.send(
                    ActionSingleEvent.ShowSnackbar(
                        UiText.StringResources(AppStrings.Snack.NoteMovedToTrash),
                    ),
                )

                cancelNoteSelection()
            }
        }

        private fun undoDelete(vararg notes: Note) {
            viewModelScope.launch {
                notes.forEach { note ->
                    restoreNoteFromTrashUseCase.invoke(note)
                }
                lastNoteAction.update { NoteAction(Action.Empty, listOf()) }
            }
        }

        fun archiveSelectedNotes(isArchived: Boolean = true) {
            archive(*_selectedNotes.value.toTypedArray(), isArchived = isArchived)
        }

        fun archiveNote(note: Note, isArchived: Boolean = true) {
            archive(note, isArchived = isArchived)
        }

        private fun archive(vararg notes: Note, isArchived: Boolean) {
            viewModelScope.launch {
                notes.forEach { note ->
                    noteRepository.updateNote(note.copy(isArchived = isArchived))
                }

                val action = if (isArchived) Action.Archive else Action.Unarchive
                val message = AppStrings.Snack.noteArchived(isArchived)

                lastNoteAction.update { NoteAction(action, notes.asList()) }

                _actionSingleEvents.send(
                    ActionSingleEvent.ShowSnackbar(UiText.StringResources(message)),
                )

                cancelNoteSelection()
            }
        }

        private fun undoArchive(vararg notes: Note, isArchived: Boolean) {
            viewModelScope.launch {
                notes.forEach { note ->
                    noteRepository.updateNote(note.copy(isArchived = isArchived.not()))
                }
                lastNoteAction.update { NoteAction(Action.Empty, listOf()) }
            }
        }

        fun undo() {
            viewModelScope.launch {
                val action = lastNoteAction.value.action
                val notes = lastNoteAction.value.notes
                when (action) {
                    Action.Archive -> {
                        undoArchive(*notes.toTypedArray(), isArchived = true)
                    }
                    Action.Unarchive -> {
                        undoArchive(*notes.toTypedArray(), isArchived = false)
                    }
                    Action.Delete -> {
                        undoDelete(*notes.toTypedArray())
                    }
                    else -> Unit
                }
            }
        }
    }

data class NoteAction(
    val action: Action = Action.Empty,
    val notes: List<Note> = listOf(),
)

sealed class Action {
    data object Delete : Action()

    data object Archive : Action()

    data object Unarchive : Action()

    data object Empty : Action()
}

sealed interface ActionSingleEvent {
    data class ShowSnackbar(val text: UiText) : ActionSingleEvent
}
