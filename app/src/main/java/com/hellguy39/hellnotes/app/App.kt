package com.hellguy39.hellnotes.app

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hellguy39.hellnotes.BuildConfig
import com.hellguy39.hellnotes.core.domain.ProjectInfoProvider
import com.hellguy39.hellnotes.core.domain.system_features.NotificationSender
import com.hellguy39.hellnotes.core.model.AppConfig
import com.hellguy39.hellnotes.work.DeleteExpiredNotesWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
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

        executeWorkers()

        //GlobalExceptionHandler.initialize(this, CrashActivity::class.java)
    }

    private fun executeWorkers() {

        val workManagerConfiguration = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

        WorkManager.initialize(this, workManagerConfiguration)

        val workManager = WorkManager.getInstance(this)

        val deleteExpiredNotesWorkerRequest =
            DeleteExpiredNotesWorker.getWorkRequest()

        workManager.enqueueUniquePeriodicWork(
            DeleteExpiredNotesWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            deleteExpiredNotesWorkerRequest
        )
    }

}