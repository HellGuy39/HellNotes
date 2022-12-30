package com.hellguy39.hellnotes.database.util

import com.hellguy39.hellnotes.database.entity.NoteEntity
import com.hellguy39.hellnotes.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        note = note,
        lastEditDate = lastEditDate,
        isPinned = isPinned,
        colorHex = colorHex,
        labels = labels
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        note = note,
        lastEditDate = lastEditDate,
        isPinned = isPinned,
        colorHex = colorHex,
        labels = labels
    )
}
