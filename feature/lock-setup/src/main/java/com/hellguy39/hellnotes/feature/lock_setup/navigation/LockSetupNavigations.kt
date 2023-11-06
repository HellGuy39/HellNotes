package com.hellguy39.hellnotes.feature.lock_setup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.lock_setup.LockSetupRoute

fun NavGraphBuilder.lockSetupScreen(
    navController: NavController
) {
    composable(
        route = Screen.LockSetup.withArgKeys(ArgumentKeys.LockType),
        arguments = listOf(
            navArgument(name = ArgumentKeys.LockType) {
                type = NavType.StringType
            }
        ),
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
        LockSetupRoute(navController)
    }
}