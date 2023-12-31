package com.hellguy39.hellnotes.feature.changelog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.changelog.ChangelogRoute

fun NavGraphBuilder.changelogScreen(appState: AppState) {
    composable(
        route = Screen.Changelog.route,
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
        ChangelogRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
