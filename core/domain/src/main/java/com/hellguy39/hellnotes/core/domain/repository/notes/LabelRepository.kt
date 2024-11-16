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

import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepository {
    suspend fun insertLabel(label: Label): Long

    suspend fun deleteLabel(label: Label)

    suspend fun deleteLabelById(id: Long)

    suspend fun updateLabel(label: Label)

    suspend fun updateLabels(labels: List<Label>)

    fun getAllLabelsStream(): Flow<List<Label>>

    suspend fun getAllLabels(): List<Label>

    suspend fun getLabelById(id: Long): Label?

    suspend fun getLabelsByNoteId(noteId: Long): List<Label>

    fun getLabelByIdFlow(id: Long): Flow<Label?>

    suspend fun deleteNoteIdFromLabels(noteId: Long)

    suspend fun deleteAll()
}
