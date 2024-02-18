package com.hellguy39.hellnotes.core.model.repository.local.datastore

data class NoteSwipesState(
    val enabled: Boolean = false,
    val swipeRight: NoteSwipe = NoteSwipe.None,
    val swipeLeft: NoteSwipe = NoteSwipe.None
)