package com.hellguy39.hellnotes.feature.reset.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.reset.ResetRoute

fun NavGraphBuilder.resetScreen(appState: AppState) {
    composable(
        route = Screen.Reset.route,
        arguments = listOf(),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.AboutApp.route -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.AboutApp.route -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) {
        ResetRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
