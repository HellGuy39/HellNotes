package com.hellguy39.hellnotes.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.hellguy39.hellnotes.BuildConfig
import com.hellguy39.hellnotes.core.domain.tools.InAppNotificationManager
import com.hellguy39.hellnotes.worker.DeleteAllExpiredNoteWrappersAtTrashWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject lateinit var workerFactory: HiltWorkerFactory

    @Inject lateinit var inAppNotificationManager: InAppNotificationManager

    private val workManager by lazy { WorkManager.getInstance(this) }

    override fun onCreate() {
        super.onCreate()

        inAppNotificationManager.init()

        configureFirebase()
        initWorkers()
    }

    private fun configureFirebase() {
        val isFirebaseEnabled = BuildConfig.DEBUG.not()

        val analytics = FirebaseAnalytics.getInstance(this)
        val crashlytics = FirebaseCrashlytics.getInstance()

        analytics.setAnalyticsCollectionEnabled(isFirebaseEnabled)
        crashlytics.setCrashlyticsCollectionEnabled(isFirebaseEnabled)
    }

    private fun initWorkers() {
        WorkManager.initialize(
            this,
            buildWorkManagerConfiguration(),
        )

        val deleteAllExpiredNoteWrappersAtTrashWorkerRequest =
            DeleteAllExpiredNoteWrappersAtTrashWorker.buildRequest()

        workManager.enqueueUniquePeriodicWork(
            DeleteAllExpiredNoteWrappersAtTrashWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            deleteAllExpiredNoteWrappersAtTrashWorkerRequest,
        )
    }

    private fun buildWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
}
