package com.hellguy39.hellnotes.core.domain.system_features

import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder

interface AlarmScheduler {

    fun scheduleAlarm(reminder: Reminder)

    fun cancelAlarm(reminder: Reminder)

}