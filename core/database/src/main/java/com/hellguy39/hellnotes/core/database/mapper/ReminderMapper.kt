package com.hellguy39.hellnotes.core.database.mapper

import com.hellguy39.hellnotes.core.database.entity.ReminderEntity
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate,
        repeat = Repeat.from(repeat),
    )
}

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        noteId = noteId,
        message = message,
        triggerDate = triggerDate,
        repeat = repeat.string(),
    )
}
