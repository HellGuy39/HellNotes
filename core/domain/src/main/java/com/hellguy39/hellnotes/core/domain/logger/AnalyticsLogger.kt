package com.hellguy39.hellnotes.core.domain.logger

import com.hellguy39.hellnotes.core.model.AnalyticsEvent

interface AnalyticsLogger {
    fun logEvent(event: AnalyticsEvent)
}
