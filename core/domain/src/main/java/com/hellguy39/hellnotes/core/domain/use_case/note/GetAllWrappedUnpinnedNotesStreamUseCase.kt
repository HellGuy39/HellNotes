package com.hellguy39.hellnotes.core.domain.use_case.note

import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.toNoteWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllWrappedUnpinnedNotesStreamUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val labelRepository: LabelRepository,
    private val reminderRepository: ReminderRepository,
    private val checklistRepository: ChecklistRepository
) {
    operator fun invoke(): Flow<List<NoteWrapper>> {
        return combine(
            noteRepository.getAllNotesStream(),
            labelRepository.getAllLabelsStream(),
            reminderRepository.getAllRemindersStream(),
            checklistRepository.getAllChecklistsStream(),
        ) { notes, labels, reminders, checklists ->
            notes.map { note -> note.toNoteWrapper(reminders, labels, checklists) }
                .sortedByDescending { noteWrapper -> noteWrapper.note.editedAt }
                .filter { noteWrapper -> !noteWrapper.note.isArchived }
                .filter { noteWrapper -> !noteWrapper.note.isPinned }
        }
            .flowOn(Dispatchers.IO)
    }
}