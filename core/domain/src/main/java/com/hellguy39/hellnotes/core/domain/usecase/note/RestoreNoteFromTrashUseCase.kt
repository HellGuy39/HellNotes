package com.hellguy39.hellnotes.core.domain.usecase.note

import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.findByIdAndEdit
import javax.inject.Inject

class RestoreNoteFromTrashUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
    ) {
        suspend operator fun invoke(noteId: Long?) {
            noteRepository.findByIdAndEdit(noteId) {
                atTrash = false
            }
        }
    }
