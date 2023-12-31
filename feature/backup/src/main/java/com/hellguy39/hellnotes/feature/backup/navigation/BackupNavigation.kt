package com.hellguy39.hellnotes.feature.backup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.backup.BackupRoute

fun NavGraphBuilder.backupScreen(appState: AppState) {
    composable(
        route = Screen.Backup.route,
        arguments = listOf(),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Settings.route -> slideEnterTransition()
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.Settings.route -> slideExitTransition()
                else -> null
            }
        },
    ) {
        BackupRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
