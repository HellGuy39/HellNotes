package com.hellguy39.hellnotes.android_features

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnalyticsLoggerImpl
@Inject
constructor(
    @ApplicationContext context: Context
) : AnalyticsLogger {

    private val analytics = FirebaseAnalytics.getInstance(context)

    override fun logEvent(name: String) {
        analytics.logEvent(name, Bundle())
    }

}