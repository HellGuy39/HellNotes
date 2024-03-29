package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import kotlinx.coroutines.flow.Flow

interface ChecklistRepository {
    suspend fun getAllChecklists(): List<Checklist>

    fun getAllChecklistsStream(): Flow<List<Checklist>>

    suspend fun insertChecklist(checklist: Checklist): Long

    suspend fun deleteChecklist(checklist: Checklist)

    suspend fun deleteChecklistById(id: Long)

    suspend fun deleteChecklistByNoteId(noteId: Long)

    suspend fun updateChecklist(checklist: Checklist)

    suspend fun updateChecklists(checklists: List<Checklist>)

    fun getChecklistByIdStream(id: Long): Flow<Checklist?>

    suspend fun getChecklistById(id: Long): Checklist?

    suspend fun getChecklistsByNoteId(noteId: Long): List<Checklist>

    fun getChecklistByNoteIdStream(noteId: Long): Flow<Checklist?>

    suspend fun deleteAll()
}
