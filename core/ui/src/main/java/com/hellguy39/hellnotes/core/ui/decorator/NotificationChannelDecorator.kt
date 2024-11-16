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
package com.hellguy39.hellnotes.core.ui.decorator

import android.content.Context
import android.content.res.Resources
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

class NotificationChannelDecorator private constructor(
    private val resources: Resources,
) {
    fun getTitle(channel: HellNotesNotificationChannel): String {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> {
                resources.getString(AppStrings.Notification.ReminderTitle)
            }
        }
    }

    fun getBody(channel: HellNotesNotificationChannel): String {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> {
                resources.getString(AppStrings.Notification.ReminderEmptyMessage)
            }
        }
    }

    fun getDescription(channel: HellNotesNotificationChannel): String {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> {
                resources.getString(AppStrings.Notification.ReminderChannelDescription)
            }
        }
    }

    fun getSmallIcon(channel: HellNotesNotificationChannel): Int {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> AppIcons.Notifications
        }
    }

    companion object {
        fun from(context: Context): NotificationChannelDecorator {
            return NotificationChannelDecorator(context.resources)
        }
    }
}
