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
package com.hellguy39.hellnotes.component.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.common.logger.taggedLogger
import com.hellguy39.hellnotes.core.domain.tools.InAppNotificationManager
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {
    @Inject
    lateinit var inAppNotificationManager: InAppNotificationManager

    private val logger by taggedLogger("NotificationActionReceiver")

    override fun onReceive(context: Context?, intent: Intent?) {
        logger.i { "Notification action received" }
        if (context == null || intent == null) return

        when (intent.action) {
            context.getString(AppStrings.Action.NotificationDone) -> {
                val notificationId = intent.getArgument(Arguments.NotificationId)
                inAppNotificationManager.cancelNotification(notificationId)
            }
            else -> {
                logger.i { "Notification action cannot recognized" }
            }
        }
    }
}
