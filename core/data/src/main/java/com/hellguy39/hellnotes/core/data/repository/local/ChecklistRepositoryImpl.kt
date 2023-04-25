package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.database.dao.ChecklistDao
import com.hellguy39.hellnotes.core.database.mapper.toChecklist
import com.hellguy39.hellnotes.core.database.mapper.toChecklistEntity
import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChecklistRepositoryImpl @Inject constructor(
    private val checklistDao: ChecklistDao
): ChecklistRepository {

    override fun getAllChecklistsStream(): Flow<List<Checklist>> {
        return checklistDao.getAllChecklistsStream()
            .map { list ->
                list.map { checklistEntity -> checklistEntity.toChecklist() }
            }
    }

    override suspend fun insertChecklist(checklist: Checklist): Long {
        return checklistDao.insertChecklist(checklist.toChecklistEntity())
    }

    override suspend fun deleteChecklist(checklist: Checklist) {
        checklistDao.deleteChecklist(checklist.toChecklistEntity())
    }

    override suspend fun updateChecklist(checklist: Checklist) {
        checklistDao.updateChecklist(checklist.toChecklistEntity())
    }

    override suspend fun updateChecklists(checklists: List<Checklist>) {
        checklistDao.updateChecklists(checklists.map { checklist -> checklist.toChecklistEntity() })
    }

    override fun getChecklistByIdStream(id: Long): Flow<Checklist> {
        return checklistDao.getChecklistByIdStream(id)
            .map { checklistEntity -> checklistEntity.toChecklist() }
    }

    override suspend fun getChecklistById(id: Long): Checklist {
        return checklistDao.getChecklistById(id).toChecklist()
    }

    override suspend fun getChecklistsByNoteId(noteId: Long): List<Checklist> {
        return checklistDao.getChecklistsByNoteId(noteId)
            .map { checklistEntity -> checklistEntity.toChecklist() }
    }

    override fun getChecklistByNoteIdStream(noteId: Long): Flow<Checklist> {
        return checklistDao.getChecklistByNoteIdStream(noteId)
            .map { checklist -> checklist.toChecklist() }
    }

    override suspend fun deleteChecklistById(id: Long) {
        checklistDao.deleteChecklistById(id)
    }

    override suspend fun deleteChecklistByNoteId(noteId: Long) {
        checklistDao.deleteChecklistByNoteId(noteId)
    }

    override suspend fun deleteAll() {
        checklistDao.deleteAll()
    }

}