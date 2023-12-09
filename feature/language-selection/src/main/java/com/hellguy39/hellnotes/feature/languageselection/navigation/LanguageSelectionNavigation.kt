package com.hellguy39.hellnotes.feature.languageselection.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.languageselection.LanguageSelectionRoute

fun NavGraphBuilder.languageSelectionScreen(appState: HellNotesAppState) {
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
