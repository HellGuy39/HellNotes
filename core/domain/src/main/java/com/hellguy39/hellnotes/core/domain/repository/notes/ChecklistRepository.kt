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
package com.hellguy39.hellnotes.core.domain.repository.notes

import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import kotlinx.coroutines.flow.Flow

interface ChecklistRepository {
    suspend fun getAllChecklists(): List<Checklist>

    fun getAllChecklistsStream(): Flow<List<Checklist>>

    suspend fun insertChecklist(checklist: Checklist): Long

    suspend fun deleteChecklist(checklist: Checklist)

    suspend fun deleteChecklistById(id: Long)

    suspend fun deleteChecklistByNoteId(noteId: Long)

    suspend fun updateChecklist(checklist: Checklist)

    suspend fun updateChecklists(checklists: List<Checklist>)

    fun getChecklistByIdStream(id: Long): Flow<Checklist?>

    suspend fun getChecklistById(id: Long): Checklist?

    suspend fun getChecklistsByNoteId(noteId: Long): List<Checklist>

    fun getChecklistByNoteIdStream(noteId: Long): Flow<Checklist?>

    suspend fun deleteAll()
}
