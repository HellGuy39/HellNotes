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

import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import javax.inject.Inject

class PostProcessNoteUseCase
    @Inject
    constructor() {
        operator fun invoke(wrapper: NoteWrapper): NoteWrapper {
            return wrapper.copy(
                note = wrapper.note.postProcessNote(),
                checklists = wrapper.checklists.postProcessChecklists(),
            )
        }

        private fun Note.postProcessNote(): Note {
            return this.copy(
                title = this.title.trim(),
                note = this.note.trim(),
            )
        }

        private fun List<Checklist>.postProcessChecklists(): List<Checklist> {
            return this.toMutableList().apply {
                for (i in this.indices) {
                    this[i] = this[i].postProcessChecklist()
                }
            }
        }

        private fun Checklist.postProcessChecklist(): Checklist {
            return this.copy(
                name = name.trim(),
                items = items.postProcessChecklistItems(),
            )
        }

        private fun List<ChecklistItem>.postProcessChecklistItems(): List<ChecklistItem> {
            return this.toMutableList().apply {
                for (i in this.indices) {
                    this[i] = this[i].postProcessChecklistItem()
                }
            }
        }

        private fun ChecklistItem.postProcessChecklistItem(): ChecklistItem {
            return this.copy(
                text = this.text.trim(),
            )
        }
    }
