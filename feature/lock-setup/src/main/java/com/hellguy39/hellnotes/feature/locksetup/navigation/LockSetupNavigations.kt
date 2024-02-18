package com.hellguy39.hellnotes.feature.locksetup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.locksetup.LockSetupRoute

fun NavGraphBuilder.lockSetupScreen(appState: AppState) {
    composable(
        route = Screen.LockSetup.withArgKeys(Arguments.Type.key),
        arguments =
            listOf(
                Arguments.Type.asNavigationArgument(),
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.LockSelection.route -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.LockSelection.route -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) {
        LockSetupRoute(
            navigateBackToSettings = {
                appState.navController
                    .popBackStack(route = Screen.Settings.route, inclusive = false)
            },
            navigateBack = { appState.navigateUp() },
        )
    }
}
