package com.hellguy39.hellnotes.core.domain.usecase.note

import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.toNoteWrapper
import javax.inject.Inject

class GetAllNoteWrappersUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val reminderRepository: ReminderRepository,
        private val checklistRepository: ChecklistRepository,
    ) {
        suspend operator fun invoke(): List<NoteWrapper> {
            val notes = noteRepository.getAllNotes()
            val labels = labelRepository.getAllLabels()
            val reminders = reminderRepository.getAllReminders()
            val checklists = checklistRepository.getAllChecklists()

            return notes.filter { note -> !note.atTrash }
                .map { note ->
                    note.toNoteWrapper(
                        labels = labels,
                        reminders = reminders,
                        checklists = checklists,
                    )
                }
        }
    }
