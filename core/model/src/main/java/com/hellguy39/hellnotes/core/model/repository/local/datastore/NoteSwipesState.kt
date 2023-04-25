package com.hellguy39.hellnotes.core.model.repository.local.datastore

data class NoteSwipesState(
    val enabled: Boolean,
    val swipeRight: NoteSwipe,
    val swipeLeft: NoteSwipe
) {
    companion object {
        fun initialInstance() = NoteSwipesState(
            enabled = false,
            swipeRight = NoteSwipe.None,
            swipeLeft = NoteSwipe.None
        )
    }
}
