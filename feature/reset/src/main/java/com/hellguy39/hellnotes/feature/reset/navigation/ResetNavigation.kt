package com.hellguy39.hellnotes.feature.reset.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.reset.ResetRoute

fun NavGraphBuilder.resetScreen(appState: HellNotesAppState) {
    composable(
        route = Screen.Reset.route,
        arguments = listOf(),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.AboutApp.route -> slideEnterTransition()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.AboutApp.route -> slideExitTransition()
                else -> null
            }
        },
    ) {
        ResetRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
