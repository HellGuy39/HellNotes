package com.hellguy39.hellnotes.feature.lock_setup.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.AnimDuration
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
            when(initialState.destination.route) {
                Screen.Settings.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(AnimDuration.medium)
                    )
                }
                else -> null
            }
        },
        exitTransition = {
            when(targetState.destination.route) {
                Screen.Settings.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(AnimDuration.fast)
                    )
                }
                else -> null
            }
        },
    ) {
        LockSetupRoute(
            navigateBackToSettings = {
                navController.popBackStack(route = Screen.Settings.route, inclusive = false)
            },
            navigateBack = { navController.popBackStack() }
        )
    }
}