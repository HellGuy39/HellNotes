package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.database.dao.RemindDao
import com.hellguy39.hellnotes.database.entity.RemindEntity
import com.hellguy39.hellnotes.database.mapper.toRemind
import com.hellguy39.hellnotes.database.mapper.toRemindEntity
import com.hellguy39.hellnotes.model.Remind
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val remindDao: RemindDao
): ReminderRepository {
    override suspend fun insertRemind(remind: Remind) {
        remindDao.insertRemind(remind.toRemindEntity())
    }

    override suspend fun deleteRemind(remind: Remind) {
        remindDao.deleteRemind(remind.toRemindEntity())
    }

    override suspend fun deleteRemindById(id: Long) {
        remindDao.deleteRemindById(id)
    }

    override suspend fun deleteRemindByNoteId(noteId: Long) {
        remindDao.deleteRemindByNoteId(noteId)
    }

    override fun getAllRemindsStream(): Flow<List<Remind>> {
        return remindDao.getAllRemindsStream().map { it.map(RemindEntity::toRemind) }
    }

    override suspend fun getRemindById(id: Long): Remind {
        return remindDao.getRemindById(id).toRemind()
    }

    override suspend fun getRemindsByNoteId(noteId: Long): List<Remind> {
        return remindDao.getRemindsByNoteId(noteId).map { it.toRemind() }
    }

    override fun getRemindsByNoteIdStream(noteId: Long): Flow<List<Remind>> {
        return remindDao.getRemindsByNoteIdStream(noteId).map { it.map(RemindEntity::toRemind) }
    }

    override suspend fun updateRemind(remind: Remind) {
        remindDao.updateRemind(remind.toRemindEntity())
    }

}