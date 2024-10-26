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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hellguy39.hellnotes.core.database.entity.NOTES_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity): Long

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: List<NoteEntity>)

    @Query(
        """
        DELETE FROM $NOTES_TABLE_NAME
        WHERE id = :id
    """,
    )
    suspend fun deleteById(id: Long)

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
    """,
    )
    suspend fun getAll(): List<NoteEntity>

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
    """,
    )
    fun getAllFlow(): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
        WHERE id = :id
        LIMIT 1
    """,
    )
    fun findByIdFlow(id: Long): Flow<NoteEntity?>

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
        WHERE id = :id
        LIMIT 1
    """,
    )
    suspend fun findById(id: Long): NoteEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteEntity: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteEntities: List<NoteEntity>)

    @Query(
        """
        DELETE FROM $NOTES_TABLE_NAME
    """,
    )
    suspend fun deleteAll()
}
