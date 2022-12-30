package com.hellguy39.hellnotes.model

data class Remind(
    val id: Int? = null,
    val noteId: Int,
    val message: Int,
    val triggerDate: Long
)
