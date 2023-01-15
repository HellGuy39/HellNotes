package com.hellguy39.hellnotes.android_features

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hellguy39.hellnotes.domain.android_system_features.NotificationSender
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings
import dagger.hilt.android.qualifiers.ApplicationContext

class AndroidNotificationSender(
    @ApplicationContext private val context: Context
) : NotificationSender {

    private val notificationManager = NotificationManagerCompat.from(context)
    private val resources = context.resources

    override fun send(
        message: String,
        pendingIntent: PendingIntent
    ) {
        val contentText = if (message.isNotBlank() && message.isNotEmpty())
            message
        else
            resources.getString(HellNotesStrings.Notification.ReminderEmptyMessage)

        val notification = NotificationCompat
            .Builder(context, NotificationSender.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(HellNotesIcons.StickyNote)
            .setContentTitle(resources.getString(HellNotesStrings.Notification.ReminderTitle))
            .setContentText(contentText)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(message.hashCode(), notification)
    }

    override fun initNotificationChannels() {

        val channel = NotificationChannel(
            NotificationSender.NOTIFICATION_CHANNEL_ID,
            NotificationSender.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.description = resources
            .getString(HellNotesStrings.Notification.ReminderChannelDescription)

        notificationManager.createNotificationChannel(channel)
    }
}