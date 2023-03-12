package com.hellguy39.hellnotes.core.model.util

sealed interface NoteSwipe {
    object None: NoteSwipe
    object DeleteOrRestore: NoteSwipe
    object ArchiveOrUnarchive: NoteSwipe

    fun parse(): String {
        return when(this) {
            is None -> NONE
            is DeleteOrRestore -> DELETE_OR_RESTORE
            is ArchiveOrUnarchive -> ARCHIVE_OR_UNARCHIVE
        }
    }

    companion object {

        const val NONE = "none"
        const val DELETE_OR_RESTORE = "delete_or_restore"
        const val ARCHIVE_OR_UNARCHIVE = "archive_or_unarchive"

        val actions = listOf(
            None,
            DeleteOrRestore,
            ArchiveOrUnarchive
        )


        fun from(s: String?, default: NoteSwipe = None) = when(s) {
            NONE -> None
            DELETE_OR_RESTORE -> DeleteOrRestore
            ARCHIVE_OR_UNARCHIVE -> ArchiveOrUnarchive
            else -> default
        }
    }

}
