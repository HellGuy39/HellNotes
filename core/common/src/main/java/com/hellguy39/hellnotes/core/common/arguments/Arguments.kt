package com.hellguy39.hellnotes.core.common.arguments

sealed class Arguments<T>(
    val key: String,
    val emptyValue: T,
) {
    data object NoteId : Arguments<Long>("note_id", -1L)

    data object ReminderId : Arguments<Long>("reminder_id", -1L)

    data object LabelId : Arguments<Long>("label_id", -1L)

    data object NotificationId : Arguments<Int>("notification_id", -1)

    data object Message : Arguments<String>("message", "")

    data object Action : Arguments<String>("action", "")

    data object Type : Arguments<String>("type", "")

    fun isEmpty(value: T?) = value == null || value == emptyValue

    fun isNotEmpty(value: T?) = value != null && value != emptyValue
}
