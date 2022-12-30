package com.hellguy39.hellnotes.model

import com.hellguy39.hellnotes.model.util.ColorParam

data class Note(
    val id: Int? = null,
    val title: String = "",
    val note: String = "",
    val lastEditDate: Long = 0,
    val isPinned: Boolean = false,
    val colorHex: Long = ColorParam.DefaultColor,
    val labels: List<String> = listOf()
)
