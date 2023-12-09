package com.hellguy39.hellnotes.feature.notedetail.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReminderEdit
import com.hellguy39.hellnotes.core.ui.values.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.values.slideExitTransition
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailRoute

fun NavGraphBuilder.noteDetailScreen(appState: HellNotesAppState) {
    composable(
        route = Screen.NoteDetail.withArgKeys(ArgumentKeys.NOTE_ID),
        arguments =
            listOf(
                navArgument(name = ArgumentKeys.NOTE_ID) {
                    type = NavType.LongType
                    defaultValue = ArgumentDefaultValues.NEW_NOTE
                },
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Home.route -> slideEnterTransition()
                Screen.Search.route -> slideEnterTransition()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.Home.route -> slideExitTransition()
                Screen.Search.route -> slideExitTransition()
                else -> null
            }
        },
    ) { from ->
        NoteDetailRoute(
            navigateBack = { appState.navigateUp() },
            navigateToNoteDetail = { id -> appState.navigateToNoteDetail(from, id) },
            navigateToLabelSelection = { id -> appState.navigateToLabelSelection(from, id) },
            navigateToReminderEdit = { noteId, reminderId ->
                appState.navigateToReminderEdit(from, noteId, reminderId)
            },
        )
    }
}
