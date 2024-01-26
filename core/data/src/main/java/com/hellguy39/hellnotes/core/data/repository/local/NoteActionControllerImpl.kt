package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.domain.repository.local.NoteActionController
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.MoveNoteToTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.RestoreNoteFromTrashUseCase
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class NoteActionControllerImpl
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val trashRepository: TrashRepository,
        private val moveNoteToTrashUseCase: MoveNoteToTrashUseCase,
        private val restoreNoteFromTrashUseCase: RestoreNoteFromTrashUseCase,
    ) : NoteActionController {
        private val lastAction =
            MutableStateFlow<Pair<NoteActionController.Action, List<Long>>?>(null)

        private val _items = MutableStateFlow(listOf<Long>())
        override val items: StateFlow<List<Long>> = _items.asStateFlow()

//        private val _singleEvents = Channel<NoteActionController.SingleEvent>()
//        override val singleEvents: Flow<NoteActionController.SingleEvent> = _singleEvents.receiveAsFlow()

        override fun select(noteId: Long) {
            _items.update { current -> current.plus(noteId) }
        }

        override fun unselect(noteId: Long) {
            _items.update { current -> current.minus(noteId) }
        }

        override fun cancel() {
            _items.update { _ -> listOf() }
        }

        override suspend fun undo() {
            val last = lastAction.value ?: return

            val action = last.first
            val noteIds = last.second

            when (action) {
                NoteActionController.Action.Archive -> {
                    undoArchive(*noteIds.toLongArray(), isArchived = true)
                }
                NoteActionController.Action.Unarchive -> {
                    undoArchive(*noteIds.toLongArray(), isArchived = false)
                }
                NoteActionController.Action.Delete -> {
                    undoDelete(*noteIds.toLongArray())
                }
                else -> Unit
            }
        }

        override suspend fun handleSwipe(noteSwipe: NoteSwipe, noteId: Long) {
            when (noteSwipe) {
                NoteSwipe.Delete -> {
                    delete(noteId)
                }
                NoteSwipe.Archive -> {
                    archive(noteId, isArchived = true)
                }
                else -> Unit
            }
        }

        override suspend fun deleteSelected() {
            delete(*_items.value.toLongArray())
        }

        private suspend fun delete(vararg noteIds: Long) {
            noteIds.forEach { noteId ->
                val note = noteRepository.getNoteById(noteId) ?: return
                moveNoteToTrashUseCase.invoke(note)
            }

            lastAction.update {
                Pair(
                    NoteActionController.Action.Delete,
                    noteIds.asList(),
                )
            }

//            _singleEvents.send(
//                NoteActionController.SingleEvent.ExecutedAction(
//                    NoteActionController.Action.Delete,
//                ),
//            )

            cancel()
        }

        private suspend fun undoDelete(vararg noteIds: Long) {
            noteIds.forEach { noteId ->
                val note = noteRepository.getNoteById(noteId) ?: return
                restoreNoteFromTrashUseCase.invoke(note)
            }
            clearLastAction()
        }

        override suspend fun archiveSelected(isArchived: Boolean) {
            archive(*_items.value.toLongArray(), isArchived = isArchived)
        }

        private suspend fun restoreNoteFromTrash(note: Note) {
            restoreNoteFromTrashUseCase.invoke(note)
        }

        private suspend fun restoreSelectedFromTrash() {
            _items.value.forEach { note ->
//                trashRepository.deleteTrashByNote(note)
//                noteRepository.insertNote(note)
            }
            cancel()
        }

        private suspend fun deleteSelectedFromTrash() {
//                selectedNotes.forEach { note ->
//                    trashRepository.deleteTrashByNote(note)
//                }
            cancel()
        }

        private suspend fun archive(vararg noteIds: Long, isArchived: Boolean) {
            noteIds.forEach { noteId ->
                val note = noteRepository.getNoteById(noteId) ?: return
                noteRepository.updateNote(note.copy(isArchived = isArchived))
            }

            val action = if (isArchived) NoteActionController.Action.Archive else NoteActionController.Action.Unarchive

            lastAction.update {
                Pair(
                    action,
                    noteIds.asList(),
                )
            }

//            _singleEvents.send(
//                NoteActionController.SingleEvent.ExecutedAction(
//                    action,
//                ),
//            )

            cancel()
        }

        private suspend fun undoArchive(vararg noteIds: Long, isArchived: Boolean) {
            noteIds.forEach { noteId ->
                val note = noteRepository.getNoteById(noteId) ?: return
                noteRepository.updateNote(note.copy(isArchived = isArchived))
            }
            clearLastAction()
        }

        private fun clearLastAction() {
            lastAction.update { Pair(NoteActionController.Action.Empty, listOf()) }
        }
    }

// class SelectableBuffer<T> {
//    private val _items = mutableListOf<T>()
//    val items: List<T>
//        get() = _items
//
//    val size: Int
//        get() = _items.size
//
//    val isEmpty: Boolean
//        get() = _items.isEmpty()
//
//    fun add(item: T): SelectableBuffer<T> {
//        return this.apply {
//            _items.add(item)
//        }
//    }
//
//    fun remove(item: T): SelectableBuffer<T> {
//        return this.apply {
//            _items.remove(item)
//        }
//    }
//
//    fun clear(): SelectableBuffer<T> {
//        return this.apply {
//            _items.clear()
//        }
//    }
//
//    fun has(item: T?): Boolean {
//        if (item == null) return false
//        return items.contains(item)
//    }
// }
