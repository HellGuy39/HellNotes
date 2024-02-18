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
