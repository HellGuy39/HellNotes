package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.model.Remind
import kotlinx.coroutines.flow.Flow

interface RemindRepository {

    suspend fun insertRemind(remind: Remind)

    suspend fun deleteRemind(remind: Remind)

    suspend fun deleteRemindById(id: Int)

    suspend fun deleteRemindByNoteId(noteId: Int)

    fun getAllReminds(): Flow<List<Remind>>

    suspend fun getRemindById(id: Int): Remind

    suspend fun getRemindByNoteId(noteId: Int): Remind

    suspend fun updateRemind(remind: Remind)

}