package com.hellguy39.hellnotes.core.data.repository.local.fake

import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.database.Trash
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeTrashRepository: TrashRepository {

    private val trashFlow = MutableStateFlow(
        mutableListOf(
            Trash(id = 0L, note = Note(), dateOfAdding = 10L),
            Trash(id = 1L, note = Note(), dateOfAdding = 10L),
            Trash(id = 2L, note = Note(), dateOfAdding = 10L),
            Trash(id = 3L, note = Note(), dateOfAdding = 10L)
        )
    )

    override fun getAllTrashStream(): Flow<List<Trash>> {
        return trashFlow
    }

    override suspend fun getAllTrash(): List<Trash> {
        return trashFlow.value
    }

    override suspend fun deleteTrash(trash: Trash) {
        trashFlow.update { trashes ->
            trashes.apply { remove(trash) }
        }
    }

    override suspend fun deleteTrashByNote(note: Note) {
        trashFlow.update { trashes ->
            trashes.apply {
                removeIf { trash -> trash.note.id == note.id }
            }
        }
    }

    override suspend fun insertTrash(trash: Trash) {
        trashFlow.update { trashes ->
            trashes.apply {
                add(trash)
            }
        }
    }

    override suspend fun deleteAll() {
        trashFlow.update { trashes -> trashes.apply { clear() } }
    }

}