package com.hellguy39.hellnotes.core.ui.analytics

import androidx.compose.runtime.staticCompositionLocalOf
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.model.AnalyticsEvent

class DefaultAnalyticsLoggerImpl : AnalyticsLogger {
    override fun logEvent(event: AnalyticsEvent) {
        println(event)
    }
}

val LocalAnalytics =
    staticCompositionLocalOf<AnalyticsLogger> {
        DefaultAnalyticsLoggerImpl()
    }
