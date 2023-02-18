package com.hellguy39.hellnotes.core.data.repository

import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.database.dao.RemindDao
import com.hellguy39.hellnotes.core.database.entity.RemindEntity
import com.hellguy39.hellnotes.core.database.mapper.toRemind
import com.hellguy39.hellnotes.core.database.mapper.toRemindEntity
import com.hellguy39.hellnotes.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val remindDao: RemindDao
): ReminderRepository {
    override suspend fun insertReminder(reminder: Reminder) {
        remindDao.insertRemind(reminder.toRemindEntity())
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        remindDao.deleteRemind(reminder.toRemindEntity())
    }

    override suspend fun deleteReminderById(id: Long) {
        remindDao.deleteRemindById(id)
    }

    override suspend fun deleteReminderByNoteId(noteId: Long) {
        remindDao.deleteRemindByNoteId(noteId)
    }

    override fun getAllRemindersStream(): Flow<List<Reminder>> {
        return remindDao.getAllRemindsStream().map { it.map(RemindEntity::toRemind) }
    }

    override suspend fun getReminderById(id: Long): Reminder {
        return remindDao.getRemindById(id).toRemind()
    }

    override suspend fun getRemindersByNoteId(noteId: Long): List<Reminder> {
        return remindDao.getRemindsByNoteId(noteId).map { it.toRemind() }
    }

    override fun getRemindersByNoteIdStream(noteId: Long): Flow<List<Reminder>> {
        return remindDao.getRemindsByNoteIdStream(noteId).map { it.map(RemindEntity::toRemind) }
    }

    override suspend fun updateReminder(reminder: Reminder) {
        remindDao.updateRemind(reminder.toRemindEntity())
    }

}