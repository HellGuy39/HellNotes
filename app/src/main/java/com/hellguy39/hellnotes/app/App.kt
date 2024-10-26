/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
