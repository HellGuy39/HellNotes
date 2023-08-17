package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.common.date.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.local.database.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val checklistRepository: ChecklistRepository,
    private val reminderRepository: ReminderRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(note: Note) {

        val id = note.id

        requireNotNull(id)

        withContext(ioDispatcher) {
            noteRepository.deleteNoteById(id)
            labelRepository.deleteNoteIdFromLabels(id)
            reminderRepository.deleteReminderByNoteId(id)
            checklistRepository.deleteChecklistByNoteId(id)
        }
    }
}