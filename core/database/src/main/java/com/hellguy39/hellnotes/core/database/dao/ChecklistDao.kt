package com.hellguy39.hellnotes.core.database.dao

import androidx.room.*
import com.hellguy39.hellnotes.core.database.entity.CHECKLIST_TABLE_NAME
import com.hellguy39.hellnotes.core.database.entity.ChecklistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {

    @Query("""
        SELECT * FROM $CHECKLIST_TABLE_NAME
    """)
    fun getAllChecklistsStream(): Flow<List<ChecklistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklist(checklistEntity: ChecklistEntity): Long

    @Delete
    suspend fun deleteChecklist(checklistEntity: ChecklistEntity)

    @Update
    suspend fun updateChecklist(checklistEntity: ChecklistEntity)

    @Update
    suspend fun updateChecklists(checklistEntities: List<ChecklistEntity>)

    @Query("""
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE id = :id
    """)
    fun getChecklistByIdStream(id: Long): Flow<ChecklistEntity>

    @Query("""
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE id = :id
    """)
    suspend fun getChecklistById(id: Long): ChecklistEntity

    @Query("""
        DELETE FROM $CHECKLIST_TABLE_NAME 
        WHERE id = :id
    """)
    suspend fun deleteChecklistById(id: Long)

    @Query("""
        DELETE FROM $CHECKLIST_TABLE_NAME 
        WHERE noteId = :noteId
    """)
    suspend fun deleteChecklistByNoteId(noteId: Long)

    @Query("""
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE noteId = :noteId
    """)
    suspend fun getChecklistsByNoteId(noteId: Long): List<ChecklistEntity>

    @Query("""
        SELECT * FROM $CHECKLIST_TABLE_NAME 
        WHERE noteId = :noteId
    """)
    fun getChecklistByNoteIdStream(noteId: Long): Flow<ChecklistEntity>

}