package com.hellguy39.hellnotes.core.domain.repository.local

import com.hellguy39.hellnotes.core.model.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepository {

    suspend fun insertLabel(label: Label): Long

    suspend fun deleteLabel(label: Label)

    suspend fun deleteLabelById(id: Long)

    suspend fun updateLabel(label: Label)

    fun getAllLabelsStream(): Flow<List<Label>>

    suspend fun getAllLabels(): List<Label>

    suspend fun getLabelById(id: Long): Label

    suspend fun deleteNoteIdFromLabels(noteId: Long)

    suspend fun deleteAll()
}