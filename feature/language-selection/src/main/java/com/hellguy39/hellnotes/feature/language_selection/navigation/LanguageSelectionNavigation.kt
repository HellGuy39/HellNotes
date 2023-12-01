package com.hellguy39.hellnotes.feature.language_selection.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.language_selection.LanguageSelectionRoute

fun NavGraphBuilder.languageSelectionScreen(
    navController: NavController
) {
    composable(
        route = Screen.LanguageSelection.route,
        arguments = listOf(),
        enterTransition = {
            UiDefaults.Motion.ScreenEnterTransition
        },
        exitTransition = {
            UiDefaults.Motion.ScreenExitTransition
        },
        popEnterTransition = {
            UiDefaults.Motion.ScreenPopEnterTransition
        },
        popExitTransition = {
            UiDefaults.Motion.ScreenPopExitTransition
        },
    ) {
        LanguageSelectionRoute(
            navigateBack = { navController.popBackStack() }
        )
    }
}
