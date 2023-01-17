package com.hellguy39.hellnotes.core.domain.system_features

import com.hellguy39.hellnotes.core.model.Remind

interface AlarmScheduler {

    fun scheduleAlarm(remind: Remind)

    fun cancelAlarm(remind: Remind)

}