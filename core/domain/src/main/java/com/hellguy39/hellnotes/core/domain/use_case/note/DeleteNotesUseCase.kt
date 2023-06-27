package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.local.database.Note
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val reminderRepository: ReminderRepository,
) {
    suspend operator fun invoke(notes: List<Note>) {
        notes.forEach { note ->
            note.id?.let { id ->
                noteRepository.deleteNoteById(id)
                labelRepository.deleteNoteIdFromLabels(id)
                reminderRepository.deleteReminderByNoteId(id)
                checklistRepository.deleteChecklistByNoteId(id)
            }
        }
    }
}