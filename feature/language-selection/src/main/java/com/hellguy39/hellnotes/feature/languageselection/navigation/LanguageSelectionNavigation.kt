package com.hellguy39.hellnotes.feature.languageselection.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.languageselection.LanguageSelectionRoute

fun NavGraphBuilder.languageSelectionScreen(appState: AppState) {
    composable(
        route = Screen.LanguageSelection.route,
        arguments = listOf(),
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
        LanguageSelectionRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
