package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.toNoteDetailWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAllNotesWithRemindersAndLabelsStreamUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val checklistRepository: ChecklistRepository
) {
    operator fun invoke(): Flow<List<NoteDetailWrapper>> {
        return combine(
            noteRepository.getAllNotesStream(),
            labelRepository.getAllLabelsStream(),
            reminderRepository.getAllRemindersStream(),
            checklistRepository.getAllChecklistsStream(),
        ) { notes, labels, reminders, checklists ->
            notes.map { note ->
                note.toNoteDetailWrapper(
                    reminders = reminders,
                    labels = labels,
                    checklists = checklists
                )
            }
        }
    }
}