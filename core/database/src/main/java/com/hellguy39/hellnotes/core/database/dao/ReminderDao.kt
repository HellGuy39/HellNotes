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
