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
    """,
    )
    suspend fun findById(id: Long): LabelEntity?

    @Query(
        """
        SELECT * FROM $LABELS_TABLE_NAME 
        WHERE id LIKE :id LIMIT 1
    """,
    )
    fun findByIdFlow(id: Long): Flow<LabelEntity?>
}
