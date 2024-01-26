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
    suspend fun insert(noteEntity: NoteEntity): Long

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: List<NoteEntity>)

    @Query(
        """
        DELETE FROM $NOTES_TABLE_NAME
        WHERE id = :id
    """,
    )
    suspend fun deleteById(id: Long)

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
    """,
    )
    suspend fun getAll(): List<NoteEntity>

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
    """,
    )
    fun getAllFlow(): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
        WHERE id = :id
        LIMIT 1
    """,
    )
    fun findByIdFlow(id: Long): Flow<NoteEntity?>

    @Query(
        """
        SELECT * FROM $NOTES_TABLE_NAME
        WHERE id = :id
        LIMIT 1
    """,
    )
    suspend fun findById(id: Long): NoteEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteEntity: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteEntities: List<NoteEntity>)

    @Query(
        """
        DELETE FROM $NOTES_TABLE_NAME
    """,
    )
    suspend fun deleteAll()
}
