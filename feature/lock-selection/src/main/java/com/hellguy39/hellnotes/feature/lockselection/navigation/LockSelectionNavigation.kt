package com.hellguy39.hellnotes.feature.lockselection.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSetup
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.lockselection.LockSelectionRoute

fun NavGraphBuilder.lockSelectionScreen(appState: HellNotesAppState) {
    composable(
        route = Screen.LockSelection.route,
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Settings.route -> slideEnterTransition()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.Settings.route -> slideExitTransition()
                else -> null
            }
        },
    ) { from ->
        LockSelectionRoute(
            navigateBack = { appState.navigateUp() },
            navigateToLockSetup = { lockType ->
                appState.navigateToLockSetup(from, lockType)
            },
        )
    }
}
