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
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNotes(noteEntity: List<NoteEntity>)

    @Query("DELETE FROM notes_table " +
            "WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table " +
            "WHERE title LIKE '%' || :query || '%' " +
            "OR note LIKE '%' || :query || '%'")
    fun getAllNotesByQuery(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table " +
            "ORDER BY lastEditDate DESC")
    fun getAllNotesSortedByDateOfLastEdit(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table " +
            "ORDER BY id DESC")
    fun getAllNotesSortedByDateOfCreation(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table " +
            "WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(noteEntity: NoteEntity)

}