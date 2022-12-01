package com.hellguy39.hellnotes.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hellguy39.hellnotes.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("DELETE FROM notes_table WHERE id = :id")
    suspend fun deleteNoteById(id: Int)

    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(noteEntity: NoteEntity)

}