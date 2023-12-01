package com.hellguy39.hellnotes.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToBackup
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLanguageSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteStyleEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteSwipeEdit
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
        SettingsRoute(
            navigateBack = { navController.popBackStack() },
            navigateToLanguageSelection = { navController.navigateToLanguageSelection() },
            navigateToLockSelection = { navController.navigateToLockSelection() },
            navigateToNoteStyleEdit = { navController.navigateToNoteStyleEdit() },
            navigateToNoteSwipeEdit = { navController.navigateToNoteSwipeEdit() },
            navigateToBackup = { navController.navigateToBackup() },
        )
    }
}
