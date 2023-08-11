package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.home.MainViewModel

fun NavGraphBuilder.notesScreen(
    mainViewModel: MainViewModel,
    onDrawerOpen: () -> Unit
) {
    composable(
        route = GraphScreen.Main.Notes.route,
    ) {
        NotesRoute(
            mainViewModel = mainViewModel,
            onDrawerOpen = onDrawerOpen
        )
    }
}