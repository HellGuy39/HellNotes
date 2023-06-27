package com.hellguy39.hellnotes.core.domain.use_case.trash

import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.TrashRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import javax.inject.Inject

class RestoreNoteFromTrashUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val trashRepository: TrashRepository
) {
    suspend operator fun invoke(note: Note) {
        restoreNote(note)
    }

    suspend operator fun invoke(noteWrapper: NoteWrapper) {
        restoreNote(noteWrapper.note)
    }

    suspend operator fun invoke(noteWrappers: List<NoteWrapper>) {
        noteWrappers.forEach { noteWrapper ->
            restoreNote(noteWrapper.note)
        }
    }

    private suspend fun restoreNote(note: Note) {
        trashRepository.deleteTrashByNote(note)
        noteRepository.insertNote(note)
    }

}