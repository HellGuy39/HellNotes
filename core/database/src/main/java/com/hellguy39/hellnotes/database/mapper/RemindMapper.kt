package com.hellguy39.hellnotes.database.mapper

import com.hellguy39.hellnotes.database.entity.RemindEntity
import com.hellguy39.hellnotes.model.Remind

fun RemindEntity.toRemind(): Remind {
    return Remind(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate
    )
}

fun Remind.toRemindEntity(): RemindEntity {
    return RemindEntity(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate
    )
}
