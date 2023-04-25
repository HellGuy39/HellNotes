package com.hellguy39.hellnotes.core.model.repository.local.database

import com.hellguy39.hellnotes.core.model.repository.local.datastore.Repeat

data class Reminder(
    val id: Long? = null,
    val noteId: Long,
    val message: String,
    val repeat: Repeat,
    val triggerDate: Long
)
