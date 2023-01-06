package com.hellguy39.hellnotes.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.domain.AlarmEvents
import com.hellguy39.hellnotes.model.Remind
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AlarmEventsImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmEvents {
    private val alarmManager: AlarmManager = context
        .getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private var receiverIntent: Intent = Intent(context, ReminderNotificationReceiver::class.java)

    override fun scheduleAlarm(remind: Remind) {
        val remindIntent = createRemindIntent(remind)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            remindIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            remind.triggerDate,
            pendingIntent
        )
    }

    override fun cancelAlarm(remind: Remind) {
        val remindIntent = createRemindIntent(remind)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            remindIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun createRemindIntent(remind: Remind): Intent {
        val remindIntent = receiverIntent
        remindIntent.putExtra(ALARM_MESSAGE, remind.message)
        remindIntent.putExtra(ALARM_NOTE_ID, remind.noteId)
        return remindIntent
    }

    companion object {
        const val ALARM_MESSAGE = "alarm_message"
        const val ALARM_NOTE_ID = "note_id"
    }
}