package com.hellguy39.hellnotes.app

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.hellguy39.hellnotes.BuildConfig
import com.hellguy39.hellnotes.core.domain.tools.InAppNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject lateinit var inAppNotificationManager: InAppNotificationManager

    override fun onCreate() {
        super.onCreate()

        inAppNotificationManager.init()

        configureFirebase()
    }

    private fun configureFirebase() {
        val isFirebaseEnabled = BuildConfig.DEBUG.not()

        val analytics = FirebaseAnalytics.getInstance(this)
        val crashlytics = FirebaseCrashlytics.getInstance()

        analytics.setAnalyticsCollectionEnabled(isFirebaseEnabled)
        crashlytics.setCrashlyticsCollectionEnabled(isFirebaseEnabled)
    }
}
