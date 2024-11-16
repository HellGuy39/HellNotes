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
