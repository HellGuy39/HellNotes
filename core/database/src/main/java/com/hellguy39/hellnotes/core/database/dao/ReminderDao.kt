package com.hellguy39.hellnotes.core.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.core.database.entity.REMINDERS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemind(remindEntity: ReminderEntity): Long

    @Delete
    suspend fun deleteRemind(remindEntity: ReminderEntity)

    @Query("""
        DELETE FROM $REMINDERS_TABLE_NAME WHERE id = :id
    """)
    suspend fun deleteRemindById(id: Long)

    @Query("""
        DELETE FROM $REMINDERS_TABLE_NAME WHERE noteId = :noteId
    """)
    suspend fun deleteRemindByNoteId(noteId: Long)

    @Query("""
        SELECT * FROM reminders_table
    """)
    fun getAllRemindsStream(): Flow<List<ReminderEntity>>

    @Query("""
        SELECT * FROM $REMINDERS_TABLE_NAME WHERE noteId = :noteId
    """)
    fun getRemindsByNoteIdStream(noteId: Long): Flow<List<ReminderEntity>>

    @Query("""
        SELECT * FROM $REMINDERS_TABLE_NAME WHERE id = :id
    """)
    suspend fun getRemindById(id: Long): ReminderEntity

    @Query("""
        SELECT * FROM $REMINDERS_TABLE_NAME 
        WHERE noteId = :noteId
    """)
    suspend fun getRemindsByNoteId(noteId: Long): List<ReminderEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRemind(remindEntity: ReminderEntity)

}