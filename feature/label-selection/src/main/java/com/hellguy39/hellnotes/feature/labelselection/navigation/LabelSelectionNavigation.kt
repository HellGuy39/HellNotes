package com.hellguy39.hellnotes.feature.labelselection.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.labelselection.LabelSelectionRoute

fun NavGraphBuilder.labelSelectionScreen(appState: HellNotesAppState) {
    composable(
        route = Screen.LabelSelection.withArgKeys(ArgumentKeys.NOTE_ID),
        arguments =
            listOf(
                navArgument(name = ArgumentKeys.NOTE_ID) {
                    type = NavType.LongType
                    defaultValue = ArgumentDefaultValues.NEW_NOTE
                },
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.NoteDetail.withArgKeys(ArgumentKeys.NOTE_ID) -> slideEnterTransition()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.NoteDetail.withArgKeys(ArgumentKeys.NOTE_ID) -> slideExitTransition()
                else -> null
            }
        },
    ) {
        LabelSelectionRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
