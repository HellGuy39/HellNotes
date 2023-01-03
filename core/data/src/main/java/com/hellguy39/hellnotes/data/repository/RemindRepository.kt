package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.model.Remind
import kotlinx.coroutines.flow.Flow

interface RemindRepository {

    suspend fun insertRemind(remind: Remind)

    suspend fun deleteRemind(remind: Remind)

    suspend fun deleteRemindById(id: Long)

    suspend fun deleteRemindByNoteId(noteId: Long)

    fun getAllReminds(): Flow<List<Remind>>

    suspend fun getRemindById(id: Long): Remind

    suspend fun getRemindsByNoteId(noteId: Long): List<Remind>

    suspend fun updateRemind(remind: Remind)

}