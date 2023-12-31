package com.hellguy39.hellnotes.feature.labelselection.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.labelselection.LabelSelectionRoute

fun NavGraphBuilder.labelSelectionScreen(appState: AppState) {
    composable(
        route = Screen.LabelSelection.withArgKeys(Arguments.NoteId.key),
        arguments =
            listOf(
                navArgument(name = Arguments.NoteId.key) {
                    type = NavType.LongType
                    defaultValue = Arguments.NoteId.emptyValue
                },
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.NoteDetail.withArgKeys(Arguments.NoteId.key) -> slideEnterTransition()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.NoteDetail.withArgKeys(Arguments.NoteId.key) -> slideExitTransition()
                else -> null
            }
        },
    ) {
        LabelSelectionRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
