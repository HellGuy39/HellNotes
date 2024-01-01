package com.hellguy39.hellnotes.feature.labelselection.navigation

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
import com.hellguy39.hellnotes.feature.labelselection.LabelSelectionRoute

fun NavGraphBuilder.labelSelectionScreen(appState: AppState) {
    composable(
        route = Screen.LabelSelection.withArgKeys(Arguments.NoteId.key),
        arguments =
            listOf(
                Arguments.NoteId.asNavigationArgument(),
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.NoteDetail.withArgKeys(Arguments.NoteId.key) -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.NoteDetail.withArgKeys(Arguments.NoteId.key) -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) {
        LabelSelectionRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
