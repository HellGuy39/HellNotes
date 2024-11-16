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

import com.hellguy39.hellnotes.core.domain.repository.notes.ChecklistRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.LabelRepository
import com.hellguy39.hellnotes.core.domain.repository.notes.NoteRepository
import com.hellguy39.hellnotes.core.domain.usecase.reminder.CreateReminderUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import javax.inject.Inject

class CopyNoteUseCase
    @Inject
    constructor(
        private val noteRepository: NoteRepository,
        private val labelRepository: LabelRepository,
        private val checklistRepository: ChecklistRepository,
        private val createReminderUseCase: CreateReminderUseCase,
    ) {
        suspend operator fun invoke(noteWrapper: NoteWrapper): Long {
            val copiedNoteId =
                noteRepository.insertNote(
                    noteWrapper.note.copy(
                        id = null,
                        createdAt = System.currentTimeMillis(),
                    ),
                )

            val copiedLabels =
                noteWrapper.labels.toMutableList().apply {
                    for (i in this.indices) {
                        val label = this[i]
                        this[i] = label.copy(noteIds = label.noteIds.plus(copiedNoteId))
                    }
                }

            labelRepository.updateLabels(copiedLabels)

            val copiedChecklists =
                noteWrapper.checklists.toMutableList().apply {
                    for (i in this.indices) {
                        val checklist = this[i]
                        this[i] =
                            checklist.copy(
                                id = null,
                                noteId = copiedNoteId,
                            )
                    }
                }

            copiedChecklists.forEach { checklist ->
                checklistRepository.insertChecklist(checklist)
            }

            noteWrapper.reminders.forEach { reminder ->
                createReminderUseCase.invoke(reminder.copy(id = null, noteId = copiedNoteId))
            }

            return copiedNoteId
        }
    }
