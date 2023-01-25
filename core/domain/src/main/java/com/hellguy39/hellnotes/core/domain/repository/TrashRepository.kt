package com.hellguy39.hellnotes.core.domain.repository

import com.hellguy39.hellnotes.core.model.Trash
import kotlinx.coroutines.flow.Flow

interface TrashRepository {

    fun getAllTrashStream(): Flow<List<Trash>>

    suspend fun deleteTrash(trash: Trash)

    suspend fun insertTrash(trash: Trash)

    suspend fun deleteAllTrash()
}