package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.RemindEntity
import com.hellguy39.hellnotes.core.model.Reminder

fun RemindEntity.toRemind(): Reminder {
    return Reminder(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate
    )
}

fun Reminder.toRemindEntity(): RemindEntity {
    return RemindEntity(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate
    )
}
