package com.hellguy39.hellnotes.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.domain.AlarmScheduler
import com.hellguy39.hellnotes.model.Remind
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduleAlarm(remind: Remind) {
        alarmManager.setExactAndAllowWhileIdle(
            ALARM_TYPE,
            remind.triggerDate,
            remind.createAlarmPendingIntent()
        )
    }

    override fun cancelAlarm(remind: Remind) {
        alarmManager.cancel(
            remind.createAlarmPendingIntent()
        )
    }

    private fun Remind.createAlarmPendingIntent(): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            this.hashCode(),
            Intent(context, ReminderNotificationReceiver::class.java).apply {
                putExtra(ALARM_MESSAGE, message)
                putExtra(ALARM_NOTE_ID, noteId)
            },
            PENDING_INTENT_FLAGS
        )
    }

    companion object {

        private const val ALARM_TYPE = AlarmManager.RTC_WAKEUP
        private const val PENDING_INTENT_FLAGS =
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        const val ALARM_MESSAGE = "alarm_message"
        const val ALARM_NOTE_ID = "note_id"
    }
}