package com.hellguy39.hellnotes.core.domain.usecase.note

import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.toNoteWrapper
import javax.inject.Inject

class GetNoteWrapperByIdUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val reminderRepository: ReminderRepository,
        private val checklistRepository: ChecklistRepository,
    ) {
        suspend operator fun invoke(noteId: Long): NoteWrapper? {
            val note = noteRepository.getNoteById(noteId)
            val labels = labelRepository.getLabelsByNoteId(noteId)
            val reminders = reminderRepository.getRemindersByNoteId(noteId)
            val checklists = checklistRepository.getChecklistsByNoteId(noteId)
            return note?.toNoteWrapper(
                reminders = reminders,
                labels = labels,
                checklists = checklists,
            )
        }
    }
