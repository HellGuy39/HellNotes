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
import com.hellguy39.hellnotes.core.database.entity.TRASH_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.TrashEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface TrashDao {
    @Query(
        """
        SELECT * FROM $TRASH_TABLE_NAME
    """,
    )
    fun getAllFlow(): Flow<List<TrashEntity>>

    @Query(
        """
        SELECT * FROM $TRASH_TABLE_NAME
    """,
    )
    suspend fun getAll(): List<TrashEntity>

    @Delete
    suspend fun delete(trashEntity: TrashEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trashEntity: TrashEntity)

    @Query(
        """
        DELETE FROM $TRASH_TABLE_NAME 
        WHERE note = :note
    """,
    )
    suspend fun deleteByNote(note: Note)

    @Query(
        """
        DELETE FROM $TRASH_TABLE_NAME
    """,
    )
    suspend fun deleteAll()
}
