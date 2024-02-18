package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed class NoteSwipe(val tag: String) {

    data object None: NoteSwipe(NONE)

    data object Delete: NoteSwipe(DELETE)

    data object Archive: NoteSwipe(ARCHIVE)

    companion object {

        const val NONE = "none"
        const val DELETE = "delete"
        const val ARCHIVE = "archive"

        fun swipes() = listOf(None, Delete, Archive)

        fun default() = None

        fun defaultSwipeRight() = Delete

        fun defaultSwipeLeft() = Archive

        fun fromTag(
            tag: String?,
            defaultValue: NoteSwipe = None
        ) = when(tag) {
            NONE -> None
            DELETE -> Delete
            ARCHIVE -> Archive
            else -> defaultValue
        }
    }
}
