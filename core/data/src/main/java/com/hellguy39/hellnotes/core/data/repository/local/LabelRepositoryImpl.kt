package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.database.dao.LabelDao
import com.hellguy39.hellnotes.core.database.entity.LabelEntity
import com.hellguy39.hellnotes.core.database.mapper.toLabel
import com.hellguy39.hellnotes.core.database.mapper.toLabelEntity
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LabelRepositoryImpl
    @Inject
    constructor(
        private val labelDao: LabelDao,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) : LabelRepository {
        override suspend fun insertLabel(label: Label): Long {
            return labelDao.insert(label.toLabelEntity())
        }

        override suspend fun deleteLabel(label: Label) {
            labelDao.delete(label.toLabelEntity())
        }

        override suspend fun deleteLabelById(id: Long) {
            labelDao.deleteById(id)
        }

        override suspend fun updateLabel(label: Label) {
            labelDao.update(label.toLabelEntity())
        }

        override suspend fun updateLabels(labels: List<Label>) {
            labelDao.update(labels.map { label -> label.toLabelEntity() })
        }

        override fun getAllLabelsStream(): Flow<List<Label>> {
            return labelDao.getAllFlow()
                .map { it.map(LabelEntity::toLabel) }
        }

        override suspend fun getLabelById(id: Long): Label? {
            return labelDao.findById(id)?.toLabel()
        }

        override suspend fun getLabelsByNoteId(noteId: Long): List<Label> {
            return labelDao.getAll()
                .filter { it.noteIds.contains(noteId) }
                .map { it.toLabel() }
        }

        override fun getLabelByIdFlow(id: Long): Flow<Label?> {
            return labelDao.findByIdFlow(id)
                .map { it?.toLabel() }
        }

        override suspend fun getAllLabels(): List<Label> {
            return labelDao.getAll()
                .map { labelEntity -> labelEntity.toLabel() }
        }

        override suspend fun deleteNoteIdFromLabels(noteId: Long) {
            val labels = labelDao.getAll()
            withContext(ioDispatcher) {
                labels.forEach { labelEntity ->
                    if (labelEntity.noteIds.contains(noteId)) {
                        val newLabel = labelEntity.copy(noteIds = labelEntity.noteIds.minus(noteId))
                        labelDao.update(newLabel)
                    }
                }
            }
        }

        override suspend fun deleteAll() {
            labelDao.deleteAll()
        }
    }
