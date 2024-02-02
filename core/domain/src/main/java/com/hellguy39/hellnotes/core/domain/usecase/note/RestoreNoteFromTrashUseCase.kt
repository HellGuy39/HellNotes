package com.hellguy39.hellnotes.core.domain.usecase.note

import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import javax.inject.Inject

class RestoreNoteFromTrashUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
    ) {
        suspend operator fun invoke(noteId: Long?) {
            if (noteId == null) return
            val note = noteRepository.getNoteById(noteId) ?: return
            noteRepository.updateNote(
                note.copy(
                    editedAt = System.currentTimeMillis(),
                    atTrash = false,
                ),
            )
        }
    }
