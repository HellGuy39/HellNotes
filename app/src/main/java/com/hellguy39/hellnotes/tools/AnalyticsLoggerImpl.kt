package com.hellguy39.hellnotes.tools

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.hellguy39.hellnotes.core.common.logger.taggedLogger
import com.hellguy39.hellnotes.core.domain.logger.AnalyticsLogger
import com.hellguy39.hellnotes.core.model.AnalyticsEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnalyticsLoggerImpl
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) : AnalyticsLogger {
        private val analytics by lazy { FirebaseAnalytics.getInstance(context) }

        private val logger by taggedLogger(TAG)

        override fun logEvent(event: AnalyticsEvent) {
            logger.d { "Analytics event logged: $event" }
            analytics.logEvent(event.type) {
                for (extra in event.extras) {
                    param(
                        key = extra.key.take(MAX_KEYS),
                        value = extra.value.take(MAX_VALUES),
                    )
                }
            }
        }

        companion object {
            private const val TAG = "AnalyticsLoggerImpl"

            private const val MAX_KEYS = 40
            private const val MAX_VALUES = 100
        }
    }
