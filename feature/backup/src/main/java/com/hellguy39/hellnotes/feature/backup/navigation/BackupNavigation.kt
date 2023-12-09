package com.hellguy39.hellnotes.feature.backup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.backup.BackupRoute

fun NavGraphBuilder.backupScreen(appState: HellNotesAppState) {
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
