package com.hellguy39.hellnotes.feature.locksetup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.locksetup.LockSetupRoute

fun NavGraphBuilder.lockSetupScreen(navController: NavController) {
    composable(
        route = Screen.LockSetup.withArgKeys(ArgumentKeys.LOCK_TYPE),
        arguments =
            listOf(
                navArgument(name = ArgumentKeys.LOCK_TYPE) {
                    type = NavType.StringType
                },
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.LockSelection.route -> slideEnterTransition()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.LockSelection.route -> slideExitTransition()
                else -> null
            }
        },
    ) {
        LockSetupRoute(
            navigateBackToSettings = {
                navController.popBackStack(route = Screen.Settings.route, inclusive = false)
            },
            navigateBack = { navController.popBackStack() },
        )
    }
}
