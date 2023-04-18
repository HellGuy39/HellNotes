package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.database.dao.LabelDao
import com.hellguy39.hellnotes.core.database.entity.LabelEntity
import com.hellguy39.hellnotes.core.database.mapper.toLabel
import com.hellguy39.hellnotes.core.database.mapper.toLabelEntity
import com.hellguy39.hellnotes.core.model.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val labelDao: LabelDao
): LabelRepository {

    override suspend fun insertLabel(label: Label): Long {
        return labelDao.insertLabel(label.toLabelEntity())
    }

    override suspend fun deleteLabel(label: Label) {
        labelDao.deleteLabel(label.toLabelEntity())
    }

    override suspend fun deleteLabelById(id: Long) {
        labelDao.deleteLabelById(id)
    }

    override suspend fun updateLabel(label: Label) {
        labelDao.updateLabel(label.toLabelEntity())
    }

    override fun getAllLabelsStream(): Flow<List<Label>> {
        return labelDao.getAllLabelsStream().map { it.map(LabelEntity::toLabel) }
    }

    override suspend fun getLabelById(id: Long): Label {
        return labelDao.getLabelById(id).toLabel()
    }

    override suspend fun getAllLabels(): List<Label> {
        return labelDao.getAllLabels().map { labelEntity -> labelEntity.toLabel() }
    }

    override suspend fun deleteNoteIdFromLabels(noteId: Long) {
        val labels = labelDao.getAllLabels()
        labels.forEach { labelEntity ->
            if (labelEntity.noteIds.contains(noteId)) {
                val newLabel = labelEntity.copy(noteIds = labelEntity.noteIds.minus(noteId))
                labelDao.updateLabel(newLabel)
            }
        }
    }

    override suspend fun deleteAll() {
        labelDao.deleteAll()
    }

}