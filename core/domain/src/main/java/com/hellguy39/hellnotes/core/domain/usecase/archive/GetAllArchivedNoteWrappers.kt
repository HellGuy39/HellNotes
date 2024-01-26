package com.hellguy39.hellnotes.core.domain.usecase.archive

import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.toNoteDetailWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllArchivedNoteWrappers
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val reminderRepository: ReminderRepository,
    ) {
        operator fun invoke() =
            combine(
                noteRepository.getAllNotesStream(),
                reminderRepository.getAllRemindersStream(),
                labelRepository.getAllLabelsStream(),
            ) { notes, reminders, labels ->
                notes.filter { note -> note.isArchived }
                    .map { note -> note.toNoteDetailWrapper(reminders, labels) }
            }
                .flowOn(Dispatchers.IO)
    }
