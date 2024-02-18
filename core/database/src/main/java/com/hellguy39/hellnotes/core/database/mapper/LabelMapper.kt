package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.LabelEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Label

fun Label.toLabelEntity(): LabelEntity {
    return LabelEntity(
        id = id,
        name = name,
        noteIds = noteIds,
    )
}

fun LabelEntity.toLabel(): Label {
    return Label(
        id = id,
        name = name,
        noteIds = noteIds,
    )
}
