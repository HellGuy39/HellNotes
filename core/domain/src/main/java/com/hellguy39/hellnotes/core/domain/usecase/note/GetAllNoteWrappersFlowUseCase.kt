/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.domain.usecase.note

import com.hellguy39.hellnotes.core.common.di.IoDispatcher
import com.hellguy39.hellnotes.core.domain.repository.notes.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.toNoteWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllNoteWrappersFlowUseCase
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
                notes.filter { note -> !note.atTrash }
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
