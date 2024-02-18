package com.hellguy39.hellnotes.core.domain.tools

import android.app.PendingIntent
import com.hellguy39.hellnotes.core.common.id.randomId
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel

interface InAppNotificationManager {
    fun init()

    fun postNotification(
        notificationId: Int,
        title: String,
        body: String,
        pendingIntent: PendingIntent,
        channel: HellNotesNotificationChannel,
    )

    fun cancelNotification(notificationId: Int)
}

fun InAppNotificationManager.postReminderNotification(
    notificationId: Int = randomId(),
    title: String = "",
    body: String = "",
    pendingIntent: PendingIntent,
) {
    postNotification(
        notificationId = notificationId,
        title = title,
        body = body,
        pendingIntent = pendingIntent,
        channel = HellNotesNotificationChannel.Reminders,
    )
}
