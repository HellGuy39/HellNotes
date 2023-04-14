package com.hellguy39.hellnotes.core.domain.repository

import com.hellguy39.hellnotes.core.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun insertReminder(reminder: Reminder): Long

    suspend fun deleteReminder(reminder: Reminder)

    suspend fun deleteReminderById(id: Long)

    suspend fun deleteReminderByNoteId(noteId: Long)

    fun getAllRemindersStream(): Flow<List<Reminder>>

    suspend fun getAllReminders(): List<Reminder>

    suspend fun getReminderById(id: Long): Reminder

    suspend fun getRemindersByNoteId(noteId: Long): List<Reminder>

    fun getRemindersByNoteIdStream(noteId: Long): Flow<List<Reminder>>

    suspend fun updateReminder(reminder: Reminder)

}