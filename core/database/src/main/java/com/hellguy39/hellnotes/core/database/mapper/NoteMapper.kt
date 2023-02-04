package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.NoteEntity
import com.hellguy39.hellnotes.core.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        note = note,
        lastEditDate = lastEditDate,
        isPinned = isPinned,
        colorHex = colorHex,
        labelIds = labelIds,
        isArchived = isArchived
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
        labelIds= labelIds,
        isArchived = isArchived
    )
}
