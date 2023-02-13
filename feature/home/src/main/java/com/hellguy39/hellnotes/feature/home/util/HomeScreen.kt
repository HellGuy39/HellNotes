package com.hellguy39.hellnotes.feature.home.util

sealed class HomeScreen(val index: Int) {
    object NoteList: HomeScreen(index = 0)
    object Reminders: HomeScreen(index = 1)
    object Archive: HomeScreen(index = 2)
    object Trash: HomeScreen(index = 3)
}
