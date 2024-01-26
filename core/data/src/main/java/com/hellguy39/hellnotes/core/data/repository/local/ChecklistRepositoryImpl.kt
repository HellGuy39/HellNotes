package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.database.dao.ChecklistDao
import com.hellguy39.hellnotes.core.database.mapper.toChecklist
import com.hellguy39.hellnotes.core.database.mapper.toChecklistEntity
import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChecklistRepositoryImpl
    @Inject
    constructor(
        private val checklistDao: ChecklistDao,
    ) : ChecklistRepository {
        override fun getAllChecklistsStream(): Flow<List<Checklist>> {
            return checklistDao.getAllFlow()
                .map { list ->
                    list.map { checklistEntity -> checklistEntity.toChecklist() }
                }
        }

        override suspend fun insertChecklist(checklist: Checklist): Long {
            return checklistDao.insert(checklist.toChecklistEntity())
        }

        override suspend fun deleteChecklist(checklist: Checklist) {
            checklistDao.delete(checklist.toChecklistEntity())
        }

        override suspend fun updateChecklist(checklist: Checklist) {
            checklistDao.update(checklist.toChecklistEntity())
        }

        override suspend fun updateChecklists(checklists: List<Checklist>) {
            checklistDao.update(checklists.map { checklist -> checklist.toChecklistEntity() })
        }

        override fun getChecklistByIdStream(id: Long): Flow<Checklist?> {
            return checklistDao.findByIdFlow(id)
                .map { checklistEntity -> checklistEntity?.toChecklist() }
        }

        override suspend fun getChecklistById(id: Long): Checklist? {
            return checklistDao.findById(id)?.toChecklist()
        }

        override suspend fun getChecklistsByNoteId(noteId: Long): List<Checklist> {
            return checklistDao.findByNoteId(noteId)
                .map { checklistEntity -> checklistEntity.toChecklist() }
        }

        override fun getChecklistByNoteIdStream(noteId: Long): Flow<Checklist?> {
            return checklistDao.findByNoteIdFlow(noteId)
                .map { checklist -> checklist?.toChecklist() }
        }

        override suspend fun deleteChecklistById(id: Long) {
            checklistDao.deleteById(id)
        }

        override suspend fun deleteChecklistByNoteId(noteId: Long) {
            checklistDao.deleteByNoteId(noteId)
        }

        override suspend fun deleteAll() {
            checklistDao.deleteAll()
        }
    }
