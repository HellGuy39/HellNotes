package com.hellguy39.hellnotes.notes.list.events

import com.hellguy39.hellnotes.model.util.Sorting

interface SortMenuEvents {
    fun show()
    fun onDismiss()
    fun onSortSelected(sorting: Sorting)
}