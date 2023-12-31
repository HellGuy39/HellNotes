package com.hellguy39.hellnotes.feature.noteswipeedit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.noteswipeedit.NoteSwipeEditRoute

fun NavGraphBuilder.noteSwipeEditScreen(appState: AppState) {
    composable(
        route = Screen.NoteSwipeEdit.route,
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Settings.route -> slideEnterTransition()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.Settings.route -> slideExitTransition()
                else -> null
            }
        },
    ) {
        NoteSwipeEditRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
