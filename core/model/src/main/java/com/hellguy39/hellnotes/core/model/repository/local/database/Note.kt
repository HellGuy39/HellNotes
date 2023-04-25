package com.hellguy39.hellnotes.core.model.repository.local.database

import com.hellguy39.hellnotes.core.model.ColorParam

data class Note(
    val id: Long? = null,
    val title: String = "",
    val note: String = "",
    val editedAt: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val isPinned: Boolean = false,
    val colorHex: Long = ColorParam.DefaultColor,
)

fun Note.isNoteValid(): Boolean {
    return note.isTextValid() || title.isTextValid()
}

private fun String.isTextValid() = this.isNotEmpty() && this.isNotBlank()