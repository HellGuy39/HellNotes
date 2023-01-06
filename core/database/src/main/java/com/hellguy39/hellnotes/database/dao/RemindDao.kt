package com.hellguy39.hellnotes.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.database.entity.RemindEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemind(remindEntity: RemindEntity)

    @Delete
    suspend fun deleteRemind(remindEntity: RemindEntity)

    @Query("DELETE FROM reminds_table WHERE id = :id")
    suspend fun deleteRemindById(id: Long)

    @Query("DELETE FROM reminds_table WHERE noteId = :noteId")
    suspend fun deleteRemindByNoteId(noteId: Long)

    @Query("SELECT * FROM reminds_table")
    fun getAllRemindsStream(): Flow<List<RemindEntity>>

    @Query("SELECT * FROM reminds_table WHERE noteId = :noteId")
    fun getRemindsByNoteIdStream(noteId: Long): Flow<List<RemindEntity>>

    @Query("SELECT * FROM reminds_table WHERE id = :id")
    suspend fun getRemindById(id: Long): RemindEntity

    @Query("SELECT * FROM reminds_table WHERE noteId = :noteId")
    suspend fun getRemindsByNoteId(noteId: Long): List<RemindEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRemind(remindEntity: RemindEntity)

}