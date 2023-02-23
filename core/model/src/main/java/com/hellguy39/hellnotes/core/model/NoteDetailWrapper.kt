package com.hellguy39.hellnotes.core.model

data class NoteDetailWrapper(
    val note: Note,
    val labels: List<Label>,
    val reminders: List<Reminder>
)

fun Note.toNoteDetailWrapper(
    reminders: List<Reminder>,
    labels: List<Label>
) = NoteDetailWrapper(
    note = this,
    labels = labels.filter { label -> label.noteIds.contains(id)  },
    reminders = reminders.filter { reminder -> id == reminder.noteId }
)