package com.hellguy39.hellnotes.core.domain.use_case.trash

import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesFromTrashStreamUseCase @Inject constructor(
    private val trashRepository: TrashRepository
) {
    operator fun invoke(): Flow<List<NoteWrapper>> {
        return trashRepository.getAllTrashStream()
            .map { trashes ->
                trashes.map { trash -> NoteWrapper(note = trash.note) }
            }
    }
}