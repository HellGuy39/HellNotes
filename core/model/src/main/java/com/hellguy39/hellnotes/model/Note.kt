package com.hellguy39.hellnotes.model

data class Note(
    val id: Int? = null,
    val title: String? = "",
    val note: String? = "",
    val lastEditDate: Long?,
    val isPinned: Boolean?,
    val labels: List<String>?
)
