package com.hellguy39.hellnotes.feature.backup.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.AnimDuration
import com.hellguy39.hellnotes.feature.backup.BackupRoute

fun NavGraphBuilder.backupScreen(
    navController: NavController
) {
    composable(
        route = Screen.Backup.route,
        arguments = listOf(),
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
        BackupRoute(
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}
