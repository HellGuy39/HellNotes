package com.hellguy39.hellnotes.core.ui.decorator

import android.content.Context
import android.content.res.Resources
import com.hellguy39.hellnotes.core.model.notification.HellNotesNotificationChannel
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

class NotificationChannelDecorator private constructor(
    private val resources: Resources,
) {
    fun getTitle(channel: HellNotesNotificationChannel): String {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> {
                resources.getString(HellNotesStrings.Notification.ReminderTitle)
            }
        }
    }

    fun getBody(channel: HellNotesNotificationChannel): String {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> {
                resources.getString(HellNotesStrings.Notification.ReminderEmptyMessage)
            }
        }
    }

    fun getDescription(channel: HellNotesNotificationChannel): String {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> {
                resources.getString(HellNotesStrings.Notification.ReminderChannelDescription)
            }
        }
    }

    fun getSmallIcon(channel: HellNotesNotificationChannel): Int {
        return when (channel) {
            HellNotesNotificationChannel.Reminders -> HellNotesIcons.Notifications
        }
    }

    companion object {
        fun from(context: Context): NotificationChannelDecorator {
            return NotificationChannelDecorator(context.resources)
        }
    }
}
