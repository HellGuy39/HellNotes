package com.hellguy39.hellnotes.core.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.core.database.entity.TrashEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface TrashDao {

    @Query("""
        SELECT * FROM trash_table
    """)
    fun getAllTrashStream(): Flow<List<TrashEntity>>

    @Query("""
        SELECT * FROM trash_table
    """)
    suspend fun getAllTrash(): List<TrashEntity>


    @Delete
    suspend fun deleteTrash(trashEntity: TrashEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrash(trashEntity: TrashEntity)

    @Query("""
        DELETE FROM trash_table 
        WHERE note = :note
    """)
    suspend fun deleteTrashByNote(note: Note)

    @Query("""
        DELETE FROM trash_table
    """)
    suspend fun deleteAll()

}