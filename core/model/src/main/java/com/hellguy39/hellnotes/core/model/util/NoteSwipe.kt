package com.hellguy39.hellnotes.core.model.util

sealed interface NoteSwipe {
    object None: NoteSwipe
    object Delete: NoteSwipe
    object Archive: NoteSwipe

    fun string(): String {
        return when(this) {
            is None -> NONE
            is Delete -> DELETE
            is Archive -> ARCHIVE
        }
    }

    companion object {

        const val NONE = "none"
        const val DELETE = "delete"
        const val ARCHIVE = "archive"

        val actions = listOf(
            None,
            Delete,
            Archive
        )


        fun from(s: String?, default: NoteSwipe = None) = when(s) {
            NONE -> None
            DELETE -> Delete
            ARCHIVE -> Archive
            else -> default
        }
    }

}
