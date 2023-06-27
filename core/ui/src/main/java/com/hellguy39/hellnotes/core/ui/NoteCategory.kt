package com.hellguy39.hellnotes.core.ui

import com.hellguy39.hellnotes.core.model.NoteWrapper

data class NoteCategory(
    val title: String = "",
    val notes: List<NoteWrapper> = listOf()
)

fun List<NoteCategory>.isSingleList(): Boolean {
    var nonEmptyCategories = 0
    forEach { category ->
        if (category.notes.isNotEmpty())
            nonEmptyCategories++
    }
    return nonEmptyCategories <= 1
}

fun List<NoteCategory>.isNotesEmpty(): Boolean {
    var nonEmptyCategories = 0
    forEach { category ->
        if (category.notes.isNotEmpty())
            nonEmptyCategories++
    }
    return nonEmptyCategories < 1
}