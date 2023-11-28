package com.hellguy39.hellnotes.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.settings.SettingsRoute

fun NavGraphBuilder.settingsScreen(
    navController: NavController
) {
    composable(
        route = Screen.Settings.route,
        arguments = listOf(),
        enterTransition = {
            null // UiDefaults.Motion.ScreenEnterTransition
        },
        exitTransition = {
            null // UiDefaults.Motion.ScreenExitTransition
        },
        popEnterTransition = {
            null // UiDefaults.Motion.ScreenPopEnterTransition
        },
        popExitTransition = {
            null // UiDefaults.Motion.ScreenPopExitTransition
        },
    ) {
        SettingsRoute(navController = navController)
    }
}
