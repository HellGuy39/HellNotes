package com.hellguy39.hellnotes.core.domain.usecase.note

import com.hellguy39.hellnotes.core.domain.repository.local.*
import javax.inject.Inject

class MoveNoteToTrashUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
    ) {
        suspend operator fun invoke(noteId: Long?) {
            noteRepository.findByIdAndEdit(noteId) {
                atTrash = true
                isArchived = false
                isPinned = false
            }
        }
    }
