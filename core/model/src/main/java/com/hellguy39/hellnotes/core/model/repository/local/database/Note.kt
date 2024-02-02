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
    val atTrash: Boolean = false,
    val colorHex: Long = ColorParam.DefaultColor,
) {
    val hasContentText: Boolean
        get() = note.isNotBlank()

    val isValid: Boolean
        get() = note.isNotBlank() || title.isNotBlank()
}
