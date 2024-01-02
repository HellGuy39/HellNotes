package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.repository.local.database.*

data class NoteDetailWrapper(
    val note: Note = Note(),
    val labels: List<Label> = listOf(),
    val reminders: List<Reminder> = listOf(),
    val checklists: List<Checklist> = listOf()
)

fun Note.toNoteDetailWrapper(
    reminders: List<Reminder> = listOf(),
    labels: List<Label> = listOf(),
    checklists: List<Checklist> = listOf()
) = NoteDetailWrapper(
    note = this,
    labels = labels.filter { label -> label.noteIds.contains(id) },
    reminders = reminders.filter { reminder -> id == reminder.noteId },
    checklists = checklists.filter { checklist -> id == checklist.noteId },
)

fun NoteDetailWrapper.isNoteWrapperInvalid(): Boolean {
    return !note.isNoteValid() && reminders.isEmpty() && labels.isEmpty() && checklists.isEmpty()
}

fun NoteDetailWrapper.hasAnythingToShow(): Boolean {
    val isTitleValid = note.title.isNotEmpty() || note.title.isNotBlank()
    val isNoteValid = note.note.isNotEmpty() || note.note.isNotBlank()
    val isChipsValid = labels.isNotEmpty() || reminders.isNotEmpty()
    val isChecklistsValid = checklists.isChecklistsValid()
    return isTitleValid || isNoteValid || isChipsValid || isChecklistsValid
}