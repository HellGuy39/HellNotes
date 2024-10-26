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
import com.hellguy39.hellnotes.core.database.entity.LABELS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.LabelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(labelEntity: LabelEntity): Long

    @Delete
    suspend fun delete(labelEntity: LabelEntity)

    @Query(
        """
        DELETE FROM $LABELS_TABLE_NAME 
    """,
    )
    suspend fun deleteAll()

    @Query(
        """
        DELETE FROM $LABELS_TABLE_NAME 
        WHERE id = :id
    """,
    )
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(labelEntity: LabelEntity)

    @Update
    suspend fun update(labelEntities: List<LabelEntity>)

    @Query(
        """
        SELECT * FROM $LABELS_TABLE_NAME
    """,
    )
    suspend fun getAll(): List<LabelEntity>

    @Query(
        """
        SELECT * FROM $LABELS_TABLE_NAME
    """,
    )
    fun getAllFlow(): Flow<List<LabelEntity>>

    @Query(
        """
        SELECT * FROM $LABELS_TABLE_NAME
        WHERE id = :id
        LIMIT 1
    """,
    )
    suspend fun findById(id: Long): LabelEntity?

    @Query(
        """
        SELECT * FROM $LABELS_TABLE_NAME 
        WHERE id LIKE :id 
        LIMIT 1
    """,
    )
    fun findByIdFlow(id: Long): Flow<LabelEntity?>
}
