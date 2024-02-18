package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import kotlinx.coroutines.flow.StateFlow

interface NoteActionController {
    val items: StateFlow<List<Long>>

    fun select(noteId: Long)

    fun unselect(noteId: Long)

    fun cancel()

    suspend fun undo()

    suspend fun moveToTrash()

    suspend fun deleteForever()

    suspend fun restoreFromTrash()

    suspend fun archive(isArchived: Boolean)

    suspend fun handleSwipe(noteSwipe: NoteSwipe, noteId: Long)

    sealed class Action {
        data object Delete : Action()

        data object Archive : Action()

        data object Unarchive : Action()

        data object Empty : Action()
    }
}
