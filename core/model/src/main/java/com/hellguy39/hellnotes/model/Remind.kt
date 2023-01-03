package com.hellguy39.hellnotes.model

data class Remind(
    val id: Long? = null,
    val noteId: Long,
    val message: String,
    val triggerDate: Long
)
