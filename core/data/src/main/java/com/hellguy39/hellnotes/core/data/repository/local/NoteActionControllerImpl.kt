/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.domain.repository.local.NoteActionController
import com.hellguy39.hellnotes.core.domain.usecase.archive.MoveNoteToArchiveUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.MoveNoteToTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.note.RestoreNoteFromTrashUseCase
import com.hellguy39.hellnotes.core.domain.usecase.trash.DeleteNoteUseCase
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class NoteActionControllerImpl
    @Inject
    constructor(
        private val moveNoteToArchiveUseCase: MoveNoteToArchiveUseCase,
        private val moveNoteToTrashUseCase: MoveNoteToTrashUseCase,
        private val restoreNoteFromTrashUseCase: RestoreNoteFromTrashUseCase,
        private val deleteNoteUseCase: DeleteNoteUseCase,
    ) : NoteActionController {
        private val lastAction =
            MutableStateFlow<Pair<NoteActionController.Action, List<Long>>?>(null)

        private val _items = MutableStateFlow(listOf<Long>())
        override val items: StateFlow<List<Long>> = _items.asStateFlow()

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
                    undoArchive(*noteIds.toLongArray(), isArchived = false)
                }
                NoteActionController.Action.Unarchive -> {
                    undoArchive(*noteIds.toLongArray(), isArchived = true)
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

        override suspend fun moveToTrash() {
            delete(*_items.value.toLongArray())
        }

        override suspend fun deleteForever() {
            _items.value.forEach { noteId ->
                deleteNoteUseCase.invoke(noteId)
            }
            cancel()
        }

        override suspend fun restoreFromTrash() {
            _items.value.forEach { noteId ->
                restoreNoteFromTrashUseCase.invoke(noteId)
            }
            cancel()
        }

        private suspend fun delete(vararg noteIds: Long) {
            noteIds.forEach { noteId ->
                moveNoteToTrashUseCase.invoke(noteId)
            }

            lastAction.update {
                Pair(
                    NoteActionController.Action.Delete,
                    noteIds.asList(),
                )
            }

            cancel()
        }

        private suspend fun undoDelete(vararg noteIds: Long) {
            noteIds.forEach { noteId ->
                restoreNoteFromTrashUseCase.invoke(noteId)
            }
            clearLastAction()
        }

        override suspend fun archive(isArchived: Boolean) {
            archive(*_items.value.toLongArray(), isArchived = isArchived)
        }

        private suspend fun archive(vararg noteIds: Long, isArchived: Boolean) {
            noteIds.forEach { noteId ->
                moveNoteToArchiveUseCase.invoke(noteId, isArchived)
            }

            val action = if (isArchived) NoteActionController.Action.Archive else NoteActionController.Action.Unarchive

            lastAction.update {
                Pair(
                    action,
                    noteIds.asList(),
                )
            }

            cancel()
        }

        private suspend fun undoArchive(vararg noteIds: Long, isArchived: Boolean) {
            noteIds.forEach { noteId ->
                moveNoteToArchiveUseCase.invoke(noteId, isArchived)
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
