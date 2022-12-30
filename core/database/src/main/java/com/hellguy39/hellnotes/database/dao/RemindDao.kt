package com.hellguy39.hellnotes.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hellguy39.hellnotes.database.entity.NoteEntity
import com.hellguy39.hellnotes.database.entity.RemindEntity
import com.hellguy39.hellnotes.model.Remind
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemind(remindEntity: RemindEntity)

    @Delete
    suspend fun deleteNote(remindEntity: RemindEntity)

    @Query("DELETE FROM reminds_table WHERE id = :id")
    suspend fun deleteRemindById(id: Int)

    @Query("DELETE FROM reminds_table WHERE noteId = :noteId")
    suspend fun deleteRemindByNoteId(noteId: Int)

    @Query("SELECT * FROM reminds_table")
    fun getAllReminds(): Flow<List<RemindEntity>>

    @Query("SELECT * FROM reminds_table WHERE id = :id")
    suspend fun getRemindById(id: Int): RemindEntity

    @Query("SELECT * FROM reminds_table WHERE noteId = :noteId")
    suspend fun getRemindByNoteId(noteId: Int): RemindEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRemind(remindEntity: RemindEntity)

}