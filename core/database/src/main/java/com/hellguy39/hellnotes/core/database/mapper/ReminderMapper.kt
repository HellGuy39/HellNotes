package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.ReminderEntity
import com.hellguy39.hellnotes.core.model.Reminder
import com.hellguy39.hellnotes.core.model.util.Repeat

fun ReminderEntity.toRemind(): Reminder {
    return Reminder(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate,
        repeat = Repeat.from(repeat)
    )
}

fun Reminder.toRemindEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate,
        repeat = repeat.string()
    )
}
