package com.hellguy39.hellnotes.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hellguy39.hellnotes.activity.MainActivity
import com.hellguy39.hellnotes.app.NOTIFICATION_CHANNEL_ID
import com.hellguy39.hellnotes.ui.HellNotesIcons

class ReminderNotificationService(
    private val context: Context
) {
    fun showNotification() {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(HellNotesIcons.Label)
            .setContentTitle("Hey")
            .setContentText("")
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)

        notificationManager.notify(1, notification)
    }
}