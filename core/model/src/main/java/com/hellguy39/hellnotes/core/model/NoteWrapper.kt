package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.local.database.Checklist
import com.hellguy39.hellnotes.core.model.local.database.Label
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.database.Reminder
import com.hellguy39.hellnotes.core.model.local.database.isNoteValid

data class NoteWrapper(
    val note: Note,
    val labels: List<Label> = listOf(),
    val reminders: List<Reminder> = listOf(),
    val checklists: List<Checklist> = listOf()
)

fun Note.toNoteWrapper(
    reminders: List<Reminder> = listOf(),
    labels: List<Label> = listOf(),
    checklists: List<Checklist> = listOf()
) = NoteWrapper(
    note = this,
    labels = labels.filter { label -> label.noteIds.contains(id) },
    reminders = reminders.filter { reminder -> id == reminder.noteId },
    checklists = checklists.filter { checklist -> id == checklist.noteId },
)

fun NoteWrapper.isNoteWrapperInvalid(): Boolean {
    return !note.isNoteValid() && reminders.isEmpty() && labels.isEmpty() && checklists.isEmpty()
}