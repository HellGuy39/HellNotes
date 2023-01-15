package com.hellguy39.hellnotes.domain.android_system_features

import com.hellguy39.hellnotes.model.Remind

interface AlarmScheduler {

    fun scheduleAlarm(remind: Remind)

    fun cancelAlarm(remind: Remind)

}