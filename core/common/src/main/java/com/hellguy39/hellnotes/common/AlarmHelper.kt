package com.hellguy39.hellnotes.common

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.hellguy39.hellnotes.model.Remind

object AlarmHelper {

    private var alarmManager: AlarmManager? = null
    private var receiverIntent: Intent? = null

    fun init(
        activity: Activity,
        receiverIntent: Intent
    ) {
        alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        this.receiverIntent = receiverIntent
    }

    fun scheduleAlarm(context: Context, remind: Remind) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            receiverIntent ?: return,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            remind.triggerDate,//System.currentTimeMillis() + (1000 * 10),
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context, remind: Remind) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            receiverIntent ?: return,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.cancel(pendingIntent)
    }

}