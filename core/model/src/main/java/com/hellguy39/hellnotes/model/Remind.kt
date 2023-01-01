package com.hellguy39.hellnotes.model

data class Remind(
    val id: Int? = null,
    val noteId: Int,
    val message: String,
    val triggerDate: Long
)
