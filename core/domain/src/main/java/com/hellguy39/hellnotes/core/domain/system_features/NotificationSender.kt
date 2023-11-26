package com.hellguy39.hellnotes.core.domain.system_features

import android.app.PendingIntent

interface NotificationSender {

    fun send(
        message: String,
        pendingIntent: PendingIntent
    )

    fun initNotificationChannels()

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "hellnotes_reminder_channel"
        const val NOTIFICATION_CHANNEL_NAME = "HellNotes reminder"
    }
}
