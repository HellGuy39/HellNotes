package com.hellguy39.hellnotes.core.model

data class NoteDetailWrapper(
    val note: Note,
    val labels: List<Label> = listOf(),
    val reminders: List<Reminder> = listOf()
)

fun Note.toNoteDetailWrapper(
    reminders: List<Reminder> = listOf(),
    labels: List<Label> = listOf()
) = NoteDetailWrapper(
    note = this,
    labels = labels.filter { label -> label.noteIds.contains(id)  },
    reminders = reminders.filter { reminder -> id == reminder.noteId }
)