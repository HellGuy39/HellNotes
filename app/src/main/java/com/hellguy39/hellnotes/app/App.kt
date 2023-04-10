package com.hellguy39.hellnotes.app

import android.app.Application
import com.hellguy39.hellnotes.BuildConfig
import com.hellguy39.hellnotes.activity.crash.CrashActivity
import com.hellguy39.hellnotes.core.domain.system_features.NotificationSender
import com.hellguy39.hellnotes.core.model.AppConfig
import com.hellguy39.hellnotes.core.ui.ProjectInfoProvider
import com.hellguy39.hellnotes.util.GlobalExceptionHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var notificationSender: NotificationSender

    override fun onCreate() {
        super.onCreate()

        notificationSender.initNotificationChannels()

        ProjectInfoProvider.setAppConfig(
            AppConfig(
                buildType = BuildConfig.BUILD_TYPE,
                applicationId = BuildConfig.APPLICATION_ID,
                versionCode = BuildConfig.VERSION_CODE,
                versionName = BuildConfig.VERSION_NAME,
                isDebug = BuildConfig.DEBUG,
            )
        )

        GlobalExceptionHandler.initialize(this, CrashActivity::class.java)
    }
}