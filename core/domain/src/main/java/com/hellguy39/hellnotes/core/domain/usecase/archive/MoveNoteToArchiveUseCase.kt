package com.hellguy39.hellnotes.core.domain.usecase.archive

import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.findByIdAndEdit
import javax.inject.Inject

class MoveNoteToArchiveUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
    ) {
        suspend operator fun invoke(noteId: Long?, newArchiveState: Boolean) {
            noteRepository.findByIdAndEdit(noteId) {
                isArchived = newArchiveState
            }
        }
    }
