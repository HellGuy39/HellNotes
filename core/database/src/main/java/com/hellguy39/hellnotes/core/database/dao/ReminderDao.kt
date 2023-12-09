package com.hellguy39.hellnotes.core.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.core.database.entity.REMINDERS_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(remindEntity: ReminderEntity): Long

    @Delete
    suspend fun deleteReminder(remindEntity: ReminderEntity)

    @Query(
        """
        DELETE FROM $REMINDERS_TABLE_NAME 
        WHERE id = :id
    """,
    )
    suspend fun deleteReminderById(id: Long)

    @Query(
        """
        DELETE FROM $REMINDERS_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    suspend fun deleteReminderByNoteId(noteId: Long)

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME
    """,
    )
    fun getAllRemindersStream(): Flow<List<ReminderEntity>>

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME
    """,
    )
    suspend fun getAllReminders(): List<ReminderEntity>

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    fun getRemindersByNoteIdStream(noteId: Long): Flow<List<ReminderEntity>>

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME 
        WHERE id = :id
    """,
    )
    suspend fun getReminderById(id: Long): ReminderEntity

    @Query(
        """
        SELECT * FROM $REMINDERS_TABLE_NAME 
        WHERE noteId = :noteId
    """,
    )
    suspend fun getRemindersByNoteId(noteId: Long): List<ReminderEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReminder(remindEntity: ReminderEntity)
}
