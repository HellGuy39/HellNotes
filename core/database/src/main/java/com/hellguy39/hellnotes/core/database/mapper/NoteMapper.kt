package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.NoteEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        note = note,
        editedAt = editedAt,
        createdAt = createdAt,
        isArchived = isArchived,
        isPinned = isPinned,
        atTrash = atTrash,
        colorHex = colorHex,
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        note = note,
        editedAt = editedAt,
        createdAt = createdAt,
        isArchived = isArchived,
        isPinned = isPinned,
        atTrash = atTrash,
        colorHex = colorHex,
    )
}
