package com.hellguy39.hellnotes.app

import android.app.Application
import com.hellguy39.hellnotes.BuildConfig
import com.hellguy39.hellnotes.common.ApplicationBuffer
import com.hellguy39.hellnotes.domain.android_system_features.NotificationSender
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var notificationSender: NotificationSender

    override fun onCreate() {
        super.onCreate()
        notificationSender.initNotificationChannels()
        ApplicationBuffer.setVersionName(BuildConfig.VERSION_NAME)
    }
}