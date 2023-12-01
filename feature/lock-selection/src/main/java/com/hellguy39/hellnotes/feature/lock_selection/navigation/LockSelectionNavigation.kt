package com.hellguy39.hellnotes.feature.lock_selection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSetup
import com.hellguy39.hellnotes.feature.lock_selection.LockSelectionRoute

fun NavGraphBuilder.lockSelectionScreen(
    navController: NavController
) {
    composable(
        route = Screen.LockSelection.route,
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
        LockSelectionRoute(
            navigateBack = { navController.popBackStack() },
            navigateToLockSetup = { lockType ->
                navController.navigateToLockSetup(lockType)
            }
        )
    }
}