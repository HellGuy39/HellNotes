package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.ChecklistEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist

fun Checklist.toChecklistEntity(): ChecklistEntity {
    return ChecklistEntity(
        id = id,
        noteId = noteId,
        items = items,
        name = name,
        isExpanded = isExpanded
    )
}

fun ChecklistEntity.toChecklist(): Checklist {
    return Checklist(
        id = id,
        noteId = noteId,
        items = items,
        name = name,
        isExpanded = isExpanded
    )
}