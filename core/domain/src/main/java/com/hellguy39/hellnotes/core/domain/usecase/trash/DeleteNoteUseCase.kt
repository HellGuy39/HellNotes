package com.hellguy39.hellnotes.core.domain.usecase.trash

import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import javax.inject.Inject

class DeleteNoteUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val checklistRepository: ChecklistRepository,
        private val reminderRepository: ReminderRepository,
    ) {
        suspend operator fun invoke(noteId: Long?) {
            if (noteId == null) return

            noteRepository.deleteNoteById(noteId)
            labelRepository.deleteNoteIdFromLabels(noteId)
            reminderRepository.deleteReminderByNoteId(noteId)
            checklistRepository.deleteChecklistByNoteId(noteId)
        }
    }
