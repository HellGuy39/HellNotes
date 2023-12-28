package com.hellguy39.hellnotes.feature.reminderedit.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.reminderedit.ReminderEditRoute

fun NavGraphBuilder.reminderEditScreen(appState: HellNotesAppState) {
    composable(
        route = Screen.ReminderEdit.withArgKeys(Arguments.NoteId.key, Arguments.ReminderId.key),
        arguments =
            listOf(
                navArgument(name = Arguments.NoteId.key) {
                    type = NavType.LongType
                    defaultValue = Arguments.NoteId.emptyValue
                },
                navArgument(name = Arguments.ReminderId.key) {
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
        ReminderEditRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
