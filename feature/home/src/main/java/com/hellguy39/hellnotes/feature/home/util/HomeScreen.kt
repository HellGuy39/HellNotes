package com.hellguy39.hellnotes.feature.home.util

sealed class HomeScreen(val index: Int) {
    data object NoteList: HomeScreen(index = 0)
    data object Reminders: HomeScreen(index = 1)
    data object Archive: HomeScreen(index = 2)
    data object Trash: HomeScreen(index = 3)
}
