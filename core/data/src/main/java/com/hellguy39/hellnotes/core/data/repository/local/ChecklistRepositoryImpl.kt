/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

        override suspend fun getAllChecklists(): List<Checklist> {
            return checklistDao.getAll().map { checklistEntity -> checklistEntity.toChecklist() }
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
