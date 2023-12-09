package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.database.Trash
import kotlinx.coroutines.flow.Flow

interface TrashRepository {
    fun getAllTrashStream(): Flow<List<Trash>>

    suspend fun getAllTrash(): List<Trash>

    suspend fun deleteTrash(trash: Trash)

    suspend fun deleteTrashByNote(note: Note)

    suspend fun insertTrash(trash: Trash)

    suspend fun deleteAll()
}
