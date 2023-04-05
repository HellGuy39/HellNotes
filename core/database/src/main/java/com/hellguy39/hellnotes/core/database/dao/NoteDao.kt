package com.hellguy39.hellnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hellguy39.hellnotes.core.database.entity.NOTES_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNotes(noteEntity: List<NoteEntity>)

    @Query("""
        DELETE FROM $NOTES_TABLE_NAME
        WHERE id = :id
    """)
    suspend fun deleteNoteById(id: Long)

    @Query("""
        SELECT * FROM $NOTES_TABLE_NAME
    """)
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("""
        SELECT * FROM $NOTES_TABLE_NAME
    """)
    fun getAllNotesStream(): Flow<List<NoteEntity>>

//    @Query("SELECT * FROM notes_table " +
//            "WHERE title LIKE '%' || :query || '%' " +
//            "OR note LIKE '%' || :query || '%'")
//    fun getAllNotesByQueryStream(query: String): Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM $NOTES_TABLE_NAME
        WHERE id = :id
    """)
    fun getNoteByIdStream(id: Long): Flow<NoteEntity>

    @Query("""
        SELECT * FROM $NOTES_TABLE_NAME
        WHERE id = :id
    """)
    suspend fun getNoteById(id: Long): NoteEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(noteEntity: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNotes(noteEntities: List<NoteEntity>)

}