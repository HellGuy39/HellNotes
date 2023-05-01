package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.TrashEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Trash

fun TrashEntity.toTrash() = Trash(
    id = id,
    note = note,
    dateOfAdding = dateOfAdding
)

fun Trash.toTrashEntity() = TrashEntity(
    id = id,
    note = note,
    dateOfAdding = dateOfAdding
)