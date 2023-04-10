package com.hellguy39.hellnotes.core.domain.use_case

import com.hellguy39.hellnotes.core.domain.repository.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.ReminderRepository
import com.hellguy39.hellnotes.core.model.Note
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val reminderRepository: ReminderRepository,
) {
    suspend operator fun invoke(
        note: Note
    ) {
        note.id?.let { id ->
            noteRepository.deleteNoteById(id)
            labelRepository.deleteNoteIdFromLabels(id)
            reminderRepository.deleteReminderByNoteId(id)
            checklistRepository.deleteChecklistByNoteId(id)
        }
    }
}