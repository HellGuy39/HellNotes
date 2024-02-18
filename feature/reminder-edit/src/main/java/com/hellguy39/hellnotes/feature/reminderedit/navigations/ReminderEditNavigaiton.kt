package com.hellguy39.hellnotes.feature.reminderedit.navigations

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
import com.hellguy39.hellnotes.feature.reminderedit.ReminderEditRoute

fun NavGraphBuilder.reminderEditScreen(appState: AppState) {
    composable(
        route = Screen.ReminderEdit.withArgKeys(Arguments.NoteId.key, Arguments.ReminderId.key),
        arguments =
            listOf(
                Arguments.NoteId.asNavigationArgument(),
                Arguments.ReminderId.asNavigationArgument(),
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
        ReminderEditRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
