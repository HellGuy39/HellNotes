package com.hellguy39.hellnotes.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.hellguy39.hellnotes.BuildConfig
import com.hellguy39.hellnotes.common.ApplicationBuffer
import dagger.hilt.android.HiltAndroidApp

const val NOTIFICATION_CHANNEL_ID = "hellnotes_reminder_channel"
const val NOTIFICATION_CHANNEL_NAME = "HellNotes reminder"

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        ApplicationBuffer.setVersionName(BuildConfig.VERSION_NAME)
    }

    private fun createNotificationChannel() {

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used for reminders"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}