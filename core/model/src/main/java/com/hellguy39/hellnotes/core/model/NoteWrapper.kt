package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.repository.local.database.*
import com.hellguy39.hellnotes.core.model.wrapper.Selectable

data class NoteWrapper(
    val note: Note = Note(),
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
    selectedIds: List<Long?> = listOf()
): List<Selectable<NoteWrapper>> {
    return map { Selectable(it, selectedIds.contains(it.note.id)) }
}