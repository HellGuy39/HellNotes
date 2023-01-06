package com.hellguy39.hellnotes.domain

import com.hellguy39.hellnotes.model.Remind

interface AlarmEvents {

    fun scheduleAlarm(remind: Remind)

    fun cancelAlarm(remind: Remind)

}