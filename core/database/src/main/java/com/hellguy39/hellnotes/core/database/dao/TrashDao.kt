package com.hellguy39.hellnotes.core.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.core.database.entity.TRASH_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.TrashEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface TrashDao {
    @Query(
        """
        SELECT * FROM $TRASH_TABLE_NAME
    """,
    )
    fun getAllFlow(): Flow<List<TrashEntity>>

    @Query(
        """
        SELECT * FROM $TRASH_TABLE_NAME
    """,
    )
    suspend fun getAll(): List<TrashEntity>

    @Delete
    suspend fun delete(trashEntity: TrashEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trashEntity: TrashEntity)

    @Query(
        """
        DELETE FROM $TRASH_TABLE_NAME 
        WHERE note = :note
    """,
    )
    suspend fun deleteByNote(note: Note)

    @Query(
        """
        DELETE FROM $TRASH_TABLE_NAME
    """,
    )
    suspend fun deleteAll()
}
