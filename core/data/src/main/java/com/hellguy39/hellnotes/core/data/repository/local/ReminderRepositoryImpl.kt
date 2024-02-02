package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.database.dao.ReminderDao
import com.hellguy39.hellnotes.core.database.entity.ReminderEntity
import com.hellguy39.hellnotes.core.database.mapper.toReminder
import com.hellguy39.hellnotes.core.database.mapper.toReminderEntity
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl
    @Inject
    constructor(
        private val reminderDao: ReminderDao,
    ) : ReminderRepository {
        override suspend fun insertReminder(reminder: Reminder): Long {
            return reminderDao.insert(reminder.toReminderEntity())
        }

        override suspend fun deleteReminder(reminder: Reminder) {
            reminderDao.delete(reminder.toReminderEntity())
        }

        override suspend fun deleteReminderById(id: Long) {
            reminderDao.deleteById(id)
        }

        override suspend fun deleteReminderByNoteId(noteId: Long) {
            reminderDao.deleteByNoteId(noteId)
        }

        override fun getAllRemindersStream(): Flow<List<Reminder>> {
            return reminderDao.getAllFlow().map { it.map(ReminderEntity::toReminder) }
        }

        override suspend fun getReminderById(id: Long): Reminder? {
            return reminderDao.findById(id)?.toReminder()
        }

        override suspend fun getRemindersByNoteId(noteId: Long): List<Reminder> {
            return reminderDao.findByNoteId(noteId).map { it.toReminder() }
        }

        override fun getRemindersByNoteIdStream(noteId: Long): Flow<List<Reminder>> {
            return reminderDao.findByNoteIdFlow(noteId).map { it.map(ReminderEntity::toReminder) }
        }

        override suspend fun updateReminder(reminder: Reminder) {
            reminderDao.update(reminder.toReminderEntity())
        }

        override suspend fun getAllReminders(): List<Reminder> {
            return reminderDao.getAll().map { reminderEntity -> reminderEntity.toReminder() }
        }
    }
