package com.hellguy39.hellnotes.core.domain.repository

import com.hellguy39.hellnotes.core.model.Remind
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun insertRemind(remind: Remind)

    suspend fun deleteRemind(remind: Remind)

    suspend fun deleteRemindById(id: Long)

    suspend fun deleteRemindByNoteId(noteId: Long)

    fun getAllRemindsStream(): Flow<List<Remind>>

    suspend fun getRemindById(id: Long): Remind

    suspend fun getRemindsByNoteId(noteId: Long): List<Remind>

    fun getRemindsByNoteIdStream(noteId: Long): Flow<List<Remind>>

    suspend fun updateRemind(remind: Remind)

}