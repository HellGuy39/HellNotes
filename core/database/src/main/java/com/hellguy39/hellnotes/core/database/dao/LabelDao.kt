package com.hellguy39.hellnotes.core.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.core.database.entity.LABELS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.LabelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(labelEntity: LabelEntity): Long

    @Delete
    suspend fun deleteLabel(labelEntity: LabelEntity)

    @Query("""
        DELETE FROM $LABELS_TABLE_NAME 
    """)
    suspend fun deleteAll()

    @Query("""
        DELETE FROM $LABELS_TABLE_NAME 
        WHERE id = :id
    """)
    suspend fun deleteLabelById(id: Long)

    @Update
    suspend fun updateLabel(labelEntity: LabelEntity)

    @Update
    suspend fun updateLabels(labelEntities: List<LabelEntity>)

    @Query("""
        SELECT * FROM $LABELS_TABLE_NAME
    """)
    suspend fun getAllLabels(): List<LabelEntity>

    @Query("""
        SELECT * FROM $LABELS_TABLE_NAME
    """)
    fun getAllLabelsStream(): Flow<List<LabelEntity>>

    @Query("""
        SELECT * FROM $LABELS_TABLE_NAME
        WHERE id = :id
    """)
    suspend fun getLabelById(id: Long): LabelEntity

}