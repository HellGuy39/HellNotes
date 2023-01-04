package com.hellguy39.hellnotes.database.mapper

import com.hellguy39.hellnotes.database.entity.LabelEntity
import com.hellguy39.hellnotes.model.Label

fun Label.toLabelEntity(): LabelEntity {
    return LabelEntity(
        id = id,
        name = name
    )
}

fun LabelEntity.toLabel(): Label {
    return Label(
        id = id,
        name = name
    )
}