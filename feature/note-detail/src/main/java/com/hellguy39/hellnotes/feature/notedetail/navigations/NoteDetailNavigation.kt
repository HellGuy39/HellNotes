package com.hellguy39.hellnotes.feature.notedetail.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationDeeplink
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReminderEdit
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailRoute

fun NavGraphBuilder.noteDetailScreen(appState: AppState) {
    composable(
        route = Screen.NoteDetail.withArgKeys(Arguments.NoteId.key),
        arguments =
            listOf(
                Arguments.NoteId.asNavigationArgument(),
            ),
        deepLinks =
            listOf(
                Arguments.NoteId.asNavigationDeeplink(),
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Home.route -> slideEnterTransition()
                Screen.Search.route -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.Home.route -> slideExitTransition()
                Screen.Search.route -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) { from ->
        NoteDetailRoute(
            navigateBack = { appState.navigateUp() },
            navigateToLabelSelection = { id -> appState.navigateToLabelSelection(from, id) },
            navigateToReminderEdit = { noteId, reminderId ->
                appState.navigateToReminderEdit(from, noteId, reminderId)
            },
        )
    }
}
