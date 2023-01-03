package com.hellguy39.hellnotes.model

import com.hellguy39.hellnotes.model.util.ColorParam

data class Note(
    val id: Long? = null,
    val title: String = "",
    val note: String = "",
    val lastEditDate: Long = 0,
    val isPinned: Boolean = false,
    val colorHex: Long = ColorParam.DefaultColor,
    val labelIds: List<Long> = listOf()
)
