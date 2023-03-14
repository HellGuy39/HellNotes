package com.hellguy39.hellnotes.core.data.repository

import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.database.dao.ReminderDao
import com.hellguy39.hellnotes.core.database.entity.ReminderEntity
import com.hellguy39.hellnotes.core.database.mapper.toRemind
import com.hellguy39.hellnotes.core.database.mapper.toRemindEntity
import com.hellguy39.hellnotes.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao
): ReminderRepository {
    override suspend fun insertReminder(reminder: Reminder): Long {
        return reminderDao.insertRemind(reminder.toRemindEntity())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteRemind(reminder.toRemindEntity())
    }

    override suspend fun deleteReminderById(id: Long) {
        reminderDao.deleteRemindById(id)
    }

    override suspend fun deleteReminderByNoteId(noteId: Long) {
        reminderDao.deleteRemindByNoteId(noteId)
    }

    override fun getAllRemindersStream(): Flow<List<Reminder>> {
        return reminderDao.getAllRemindsStream().map { it.map(ReminderEntity::toRemind) }
    }

    override suspend fun getReminderById(id: Long): Reminder {
        return reminderDao.getRemindById(id).toRemind()
    }

    override suspend fun getRemindersByNoteId(noteId: Long): List<Reminder> {
        return reminderDao.getRemindsByNoteId(noteId).map { it.toRemind() }
    }

    override fun getRemindersByNoteIdStream(noteId: Long): Flow<List<Reminder>> {
        return reminderDao.getRemindsByNoteIdStream(noteId).map { it.map(ReminderEntity::toRemind) }
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateRemind(reminder.toRemindEntity())
    }

}