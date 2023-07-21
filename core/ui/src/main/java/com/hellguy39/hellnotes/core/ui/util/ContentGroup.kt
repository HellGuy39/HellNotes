package com.hellguy39.hellnotes.core.ui.util

data class ContentGroup<T>(
    val name: String = "",
    val content: List<T> = listOf()
)

fun <T> List<ContentGroup<T>>.isSingleGroup(): Boolean {
    var nonEmptyCategories = 0
    for (contentGroup in this) {
        if (contentGroup.content.isNotEmpty()) {
            nonEmptyCategories++
        }
    }
    return nonEmptyCategories <= 1
}
//
//fun List<NoteCategory>.isNotesEmpty(): Boolean {
//    var nonEmptyCategories = 0
//    forEach { category ->
//        if (category.notes.isNotEmpty())
//            nonEmptyCategories++
//    }
//    return nonEmptyCategories < 1
//}