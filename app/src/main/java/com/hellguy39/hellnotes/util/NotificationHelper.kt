package com.hellguy39.hellnotes.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hellguy39.hellnotes.activity.MainActivity
import com.hellguy39.hellnotes.app.NOTIFICATION_CHANNEL_ID
import com.hellguy39.hellnotes.resources.HellNotesIcons
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showRemindNotification(intent: Intent?) {

        val message = intent?.extras?.getString(AlarmEventsImpl.ALARM_MESSAGE)
        val noteId = intent?.extras?.getLong(AlarmEventsImpl.ALARM_NOTE_ID)

        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.putExtra(AlarmEventsImpl.ALARM_NOTE_ID, noteId)

        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(HellNotesIcons.Notifications)
            .setContentTitle(message)
            // .setContentText("")
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }
}