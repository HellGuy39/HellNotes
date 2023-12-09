package com.hellguy39.hellnotes.core.domain.tools

import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder

interface AlarmScheduler {
    fun scheduleAlarm(reminder: Reminder)

    fun cancelAlarm(reminder: Reminder)
}
