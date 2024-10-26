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
package com.hellguy39.hellnotes.core.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.core.database.entity.CHECKLIST_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.ChecklistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {
    @Query(
        """
        SELECT * FROM $CHECKLIST_TABLE_NAME
    """,
    )
    fun getAllFlow(): Flow<List<ChecklistEntity>>

    @Query(
        """
        SELECT * FROM $CHECKLIST_TABLE_NAME
    """,
    )
    suspend fun getAll(): List<ChecklistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checklistEntity: ChecklistEntity): Long

    @Delete
    suspend fun delete(checklistEntity: ChecklistEntity)

    @Update
    suspend fun update(checklistEntity: ChecklistEntity)

    @Update
    suspend fun update(checklistEntities: List<ChecklistEntity>)

    @Query(
        """
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE id = :id
        LIMIT 1
    """,
    )
    fun findByIdFlow(id: Long): Flow<ChecklistEntity?>

    @Query(
        """
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE id = :id
        LIMIT 1
    """,
    )
    suspend fun findById(id: Long): ChecklistEntity?

    @Query(
        """
        DELETE FROM $CHECKLIST_TABLE_NAME 
        WHERE id = :id
    """,
    )
    suspend fun deleteById(id: Long)

    @Query(
        """
        DELETE FROM $CHECKLIST_TABLE_NAME 
    """,
    )
    suspend fun deleteAll()

    @Query(
        """
        DELETE FROM $CHECKLIST_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    suspend fun deleteByNoteId(noteId: Long)

    @Query(
        """
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    suspend fun findByNoteId(noteId: Long): List<ChecklistEntity>

    @Query(
        """
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE noteId = :noteId
        LIMIT 1
    """,
    )
    fun findByNoteIdFlow(noteId: Long): Flow<ChecklistEntity?>
}
