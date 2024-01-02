package com.hellguy39.hellnotes.core.ui.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger

@Composable
fun TrackScreenView(
    screenName: String,
    analyticsLogger: AnalyticsLogger = LocalAnalytics.current,
) = DisposableEffect(Unit) {
    analyticsLogger.logScreenView(screenName)
    onDispose { /* no-op */ }
}
