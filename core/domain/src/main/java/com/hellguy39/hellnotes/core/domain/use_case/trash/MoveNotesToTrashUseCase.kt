package com.hellguy39.hellnotes.core.domain.use_case.trash

import com.hellguy39.hellnotes.core.domain.repository.local.*
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.database.Trash
import javax.inject.Inject

class MoveNotesToTrashUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val reminderRepository: ReminderRepository,
    private val trashRepository: TrashRepository
) {

    suspend operator fun invoke(noteWrappers: List<NoteWrapper>) {
        noteWrappers.forEach { wrapper -> removeNote(wrapper.note) }
    }

    private suspend fun removeNote(note: Note) {
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