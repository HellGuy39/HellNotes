package com.hellguy39.hellnotes.core.data.repository.local

import com.hellguy39.hellnotes.core.database.dao.TrashDao
import com.hellguy39.hellnotes.core.database.entity.TrashEntity
import com.hellguy39.hellnotes.core.database.mapper.toTrash
import com.hellguy39.hellnotes.core.database.mapper.toTrashEntity
import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.database.Trash
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrashRepositoryImpl @Inject constructor(
    private val trashDao: TrashDao
): TrashRepository {

    override fun getAllTrashStream(): Flow<List<Trash>> =
        trashDao.getAllTrashStream().map { it.map(TrashEntity::toTrash) }

    override suspend fun getAllTrash(): List<Trash> {
        return trashDao.getAllTrash().map { it.toTrash() }
    }

    override suspend fun deleteTrash(trash: Trash) {
        trashDao.deleteTrash(trash.toTrashEntity())
    }

    override suspend fun deleteTrashByNote(note: Note) {
        trashDao.deleteTrashByNote(note)
    }

    override suspend fun insertTrash(trash: Trash) {
        trashDao.insertTrash(trash.toTrashEntity())
    }

    override suspend fun deleteAll() {
        trashDao.deleteAll()
    }
}