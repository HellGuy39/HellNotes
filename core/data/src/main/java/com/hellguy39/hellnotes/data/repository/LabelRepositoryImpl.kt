package com.hellguy39.hellnotes.data.repository

import com.hellguy39.hellnotes.domain.repository.LabelRepository
import com.hellguy39.hellnotes.database.dao.LabelDao
import com.hellguy39.hellnotes.database.entity.LabelEntity
import com.hellguy39.hellnotes.database.mapper.toLabel
import com.hellguy39.hellnotes.database.mapper.toLabelEntity
import com.hellguy39.hellnotes.model.Label
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

    override suspend fun updateLabel(label: Label) {
        labelDao.updateLabel(label.toLabelEntity())
    }

    override fun getAllLabelsStream(query: String): Flow<List<Label>> {
        return labelDao.getAllLabelsStream(query).map { it.map(LabelEntity::toLabel) }
    }

    override suspend fun getLabelById(id: Long): Label {
        return labelDao.getLabelById(id).toLabel()
    }
}