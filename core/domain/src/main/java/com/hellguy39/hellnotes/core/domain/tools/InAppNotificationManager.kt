package com.hellguy39.hellnotes.core.domain.tools

import android.app.PendingIntent
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel

interface InAppNotificationManager {
    fun init()

    fun postNotification(
        title: String = "",
        body: String = "",
        pendingIntent: PendingIntent,
        channel: HellNotesNotificationChannel,
    )
}

fun InAppNotificationManager.postReminderNotification(
    title: String = "",
    body: String = "",
    pendingIntent: PendingIntent,
) {
    postNotification(
        title = title,
        body = body,
        pendingIntent = pendingIntent,
        channel = HellNotesNotificationChannel.Reminders,
    )
}
