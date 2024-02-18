package com.hellguy39.hellnotes.core.ui.analytics

import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.model.AnalyticsEvent
import com.hellguy39.hellnotes.core.model.AnalyticsEvent.*

fun AnalyticsLogger.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = Types.SCREEN_VIEW,
            extras =
                listOf(
                    Param(ParamKeys.SCREEN_NAME, screenName),
                ),
        ),
    )
}

fun AnalyticsLogger.buttonClick(screenName: String, buttonId: String) {
    logEvent(
        AnalyticsEvent(
            type = Types.BUTTON_CLICK,
            extras =
                listOf(
                    Param(ParamKeys.SCREEN_NAME, screenName),
                    Param(ParamKeys.BUTTON_ID, buttonId),
                ),
        ),
    )
}
