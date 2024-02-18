package com.hellguy39.hellnotes.core.domain.usecase.trash

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.toNoteWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNoteWrappersAtTrashFlowUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val reminderRepository: ReminderRepository,
        private val checklistRepository: ChecklistRepository,
        @IoDispatcher
        private val ioDispatcher: CoroutineDispatcher,
    ) {
        operator fun invoke(): Flow<List<NoteWrapper>> {
            return combine(
                noteRepository.getAllNotesStream(),
                labelRepository.getAllLabelsStream(),
                reminderRepository.getAllRemindersStream(),
                checklistRepository.getAllChecklistsStream(),
            ) { notes, labels, reminders, checklists ->
                notes.filter { note -> note.atTrash }
                    .map { note ->
                        note.toNoteWrapper(
                            reminders = reminders,
                            labels = labels,
                            checklists = checklists,
                        )
                    }
            }
                .flowOn(ioDispatcher)
        }
    }
