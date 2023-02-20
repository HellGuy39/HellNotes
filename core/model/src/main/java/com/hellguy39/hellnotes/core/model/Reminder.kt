package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.util.Repeat

data class Reminder(
    val id: Long? = null,
    val noteId: Long,
    val message: String,
    val repeat: Repeat,
    val triggerDate: Long
)
