package com.hellguy39.hellnotes.core.domain.use_case

import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.TrashRepository
import com.hellguy39.hellnotes.core.model.Note
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