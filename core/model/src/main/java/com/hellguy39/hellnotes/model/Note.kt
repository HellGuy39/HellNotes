package com.hellguy39.hellnotes.model

data class Note(
    val id: Int? = null,
    val title: String = "",
    val note: String = "",
    val lastEditDate: Long = 0,
    val isPinned: Boolean = false,
    val labels: List<String> = listOf()
)
