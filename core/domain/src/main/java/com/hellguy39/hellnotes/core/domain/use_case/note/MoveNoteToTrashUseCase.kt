package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.domain.repository.local.*
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.database.Trash
import javax.inject.Inject

class MoveNoteToTrashUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository
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

        trashRepository.insertTrash(
            Trash(
                note = note,
                dateOfAdding = System.currentTimeMillis()
            )
        )
    }
}