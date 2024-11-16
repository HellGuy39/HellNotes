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
import com.hellguy39.hellnotes.core.database.entity.REMINDERS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remindEntity: ReminderEntity): Long

    @Delete
    suspend fun delete(remindEntity: ReminderEntity)

    @Query(
        """
        DELETE FROM $REMINDERS_TABLE_NAME 
        WHERE id = :id
    """,
    )
    suspend fun deleteById(id: Long)

    @Query(
        """
        DELETE FROM $REMINDERS_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    suspend fun deleteByNoteId(noteId: Long)

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME
    """,
    )
    fun getAllFlow(): Flow<List<ReminderEntity>>

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME
    """,
    )
    suspend fun getAll(): List<ReminderEntity>

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    fun findByNoteIdFlow(noteId: Long): Flow<List<ReminderEntity>>

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME 
        WHERE id = :id
        LIMIT 1
    """,
    )
    suspend fun findById(id: Long): ReminderEntity?

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    suspend fun findByNoteId(noteId: Long): List<ReminderEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(remindEntity: ReminderEntity)
}
