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
package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.repository.local.database.*
import com.hellguy39.hellnotes.core.model.wrapper.Selectable

data class NoteWrapper(
    val note: Note = Note(),
    val labels: List<Label> = listOf(),
    val reminders: List<Reminder> = listOf(),
    val checklists: List<Checklist> = listOf(),
)

fun Note.toNoteWrapper(
    reminders: List<Reminder> = listOf(),
    labels: List<Label> = listOf(),
    checklists: List<Checklist> = listOf(),
) = NoteWrapper(
    note = this,
    labels = labels.filter { label -> label.noteIds.contains(id) },
    reminders = reminders.filter { reminder -> id == reminder.noteId },
    checklists = checklists.filter { checklist -> id == checklist.noteId },
)

fun NoteWrapper.isNoteWrapperInvalid(): Boolean {
    return !note.isValid && reminders.isEmpty() && labels.isEmpty() && checklists.isEmpty()
}

fun NoteWrapper.hasAnythingToShow(): Boolean {
    val isTitleValid = note.title.isNotEmpty() || note.title.isNotBlank()
    val isNoteValid = note.note.isNotEmpty() || note.note.isNotBlank()
    val isChipsValid = labels.isNotEmpty() || reminders.isNotEmpty()
    val isChecklistsValid = checklists.isChecklistsValid()
    return isTitleValid || isNoteValid || isChipsValid || isChecklistsValid
}

fun List<NoteWrapper>.toSelectable(
    selectedIds: List<Long?> = listOf(),
): List<Selectable<NoteWrapper>> {
    return map { Selectable(it, selectedIds.contains(it.note.id)) }
}
