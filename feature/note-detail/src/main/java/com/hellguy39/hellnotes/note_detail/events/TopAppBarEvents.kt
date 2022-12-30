package com.hellguy39.hellnotes.note_detail.events

interface TopAppBarEvents {
    fun onReminder()
    fun onPin(isPinned: Boolean)
    fun onColorSelected(colorHex: Long)
    fun onMoreMenu()
}