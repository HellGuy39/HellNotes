package com.hellguy39.hellnotes.feature.update.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.AnimDuration
import com.hellguy39.hellnotes.feature.update.UpdateRoute

fun NavGraphBuilder.updateScreen(
    navController: NavController
) {
    composable(
        route = Screen.Update.route,
        arguments = listOf(),
        enterTransition = {
            when(initialState.destination.route) {
                Screen.AboutApp.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(AnimDuration.medium)
                    )
                }
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when(targetState.destination.route) {
                Screen.AboutApp.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(AnimDuration.fast)
                    )
                }
                else -> null
            }
        }
    ) {
        UpdateRoute(
            navigateBack = { navController.popBackStack() }
        )
    }
}
