package com.hellguy39.hellnotes.feature.settings.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.settings.SettingsRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsScreen(
    navController: NavController
) {
    composable(
        route = Screen.Settings.route,
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
        SettingsRoute(navController = navController)
    }
}
