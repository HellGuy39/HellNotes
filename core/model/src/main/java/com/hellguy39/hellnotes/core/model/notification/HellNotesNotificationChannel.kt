package com.hellguy39.hellnotes.core.model.notification

sealed class HellNotesNotificationChannel(val info: NotificationChannelInfo) {

    data object Reminders : HellNotesNotificationChannel(
        NotificationChannelInfo(
            id = "hellnotes_reminder_channel", // TODO: check that name changes
            name = "Reminders",
            importance = 3 // Default
        )
    )

    companion object {

        fun asList() : List<HellNotesNotificationChannel> {
            return listOf(
                Reminders
            )
        }
    }
}