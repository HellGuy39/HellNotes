package com.hellguy39.hellnotes.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToBackup
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLanguageSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteStyleEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteSwipeEdit
import com.hellguy39.hellnotes.feature.settings.SettingsRoute

fun NavGraphBuilder.settingsScreen(
    appState: HellNotesAppState,
) {
    composable(
        route = Screen.Settings.route,
        arguments = listOf(),
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) { from ->
        SettingsRoute(
            navigateBack = { appState.navigateUp() },
            navigateToLanguageSelection = { appState.navigateToLanguageSelection(from) },
            navigateToLockSelection = { appState.navigateToLockSelection(from) },
            navigateToNoteStyleEdit = { appState.navigateToNoteStyleEdit(from) },
            navigateToNoteSwipeEdit = { appState.navigateToNoteSwipeEdit(from) },
            navigateToBackup = { appState.navigateToBackup(from) },
        )
    }
}
