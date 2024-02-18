package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepository {
    suspend fun insertLabel(label: Label): Long

    suspend fun deleteLabel(label: Label)

    suspend fun deleteLabelById(id: Long)

    suspend fun updateLabel(label: Label)

    suspend fun updateLabels(labels: List<Label>)

    fun getAllLabelsStream(): Flow<List<Label>>

    suspend fun getAllLabels(): List<Label>

    suspend fun getLabelById(id: Long): Label?

    suspend fun getLabelsByNoteId(noteId: Long): List<Label>

    fun getLabelByIdFlow(id: Long): Flow<Label?>

    suspend fun deleteNoteIdFromLabels(noteId: Long)

    suspend fun deleteAll()
}
