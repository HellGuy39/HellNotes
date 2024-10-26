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
package com.hellguy39.hellnotes.core.domain.usecase.trash

import com.hellguy39.hellnotes.core.domain.repository.local.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.local.NoteRepository
import com.hellguy39.hellnotes.core.domain.repository.local.ReminderRepository
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.toNoteWrapper
import javax.inject.Inject

class GetAllNoteWrappersAtTrashUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val reminderRepository: ReminderRepository,
        private val checklistRepository: ChecklistRepository,
    ) {
        suspend operator fun invoke(): List<NoteWrapper> {
            val notes = noteRepository.getAllNotes()
            val labels = labelRepository.getAllLabels()
            val reminders = reminderRepository.getAllReminders()
            val checklists = checklistRepository.getAllChecklists()

            return notes.filter { note -> note.atTrash }
                .map { note ->
                    note.toNoteWrapper(
                        labels = labels,
                        reminders = reminders,
                        checklists = checklists,
                    )
                }
        }
    }
