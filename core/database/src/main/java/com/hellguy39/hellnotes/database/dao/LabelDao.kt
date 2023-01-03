package com.hellguy39.hellnotes.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.database.entity.LabelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(labelEntity: LabelEntity): Long

    @Delete
    suspend fun deleteLabel(labelEntity: LabelEntity)

    @Update
    suspend fun updateLabel(labelEntity: LabelEntity)

    @Query("SELECT * FROM labels_table " +
            "WHERE name LIKE '%' || :query || '%'")
    fun getAllLabelsStream(query: String): Flow<List<LabelEntity>>

    @Query("SELECT * FROM labels_table " +
            "WHERE id = :id")
    suspend fun getLabelById(id: Long): LabelEntity

}