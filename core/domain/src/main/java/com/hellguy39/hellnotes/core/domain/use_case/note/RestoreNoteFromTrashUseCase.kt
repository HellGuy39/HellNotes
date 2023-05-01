package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import javax.inject.Inject

class RestoreNoteFromTrashUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val trashRepository: TrashRepository
) {
    suspend operator fun invoke(
        note: Note
    ) {
        trashRepository.deleteTrashByNote(note)
        noteRepository.insertNote(note)
    }
}