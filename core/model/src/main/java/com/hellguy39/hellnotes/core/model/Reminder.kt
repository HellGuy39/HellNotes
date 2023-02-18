package com.hellguy39.hellnotes.core.model

data class Reminder(
    val id: Long? = null,
    val noteId: Long,
    val message: String,
    val triggerDate: Long
)
