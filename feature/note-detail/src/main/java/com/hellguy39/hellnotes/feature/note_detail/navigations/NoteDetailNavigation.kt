package com.hellguy39.hellnotes.feature.note_detail.navigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReminderEdit
import com.hellguy39.hellnotes.feature.note_detail.NoteDetailRoute

fun NavGraphBuilder.noteDetailScreen(
    navController: NavController
) {
    composable(
        route = Screen.NoteDetail.withArgKeys(ArgumentKeys.NoteId),
        arguments = listOf(
            navArgument(name = ArgumentKeys.NoteId) {
                type = NavType.LongType
                defaultValue = ArgumentDefaultValues.NewNote
            }
        ),
        enterTransition = {
            UiDefaults.Motion.ScreenEnterTransition
        },
        exitTransition = {
            UiDefaults.Motion.ScreenExitTransition
        },
        popEnterTransition = {
            UiDefaults.Motion.ScreenPopEnterTransition
        },
        popExitTransition = {
            UiDefaults.Motion.ScreenPopExitTransition
        },
    ) {
        NoteDetailRoute(
            navigateBack = { navController.popBackStack() },
            navigateToNoteDetail = { id -> navController.navigateToNoteDetail(id) },
            navigateToLabelSelection = { id -> navController.navigateToLabelSelection(id) },
            navigateToReminderEdit = { noteId, reminderId ->
                navController.navigateToReminderEdit(
                    noteId = noteId,
                    reminderId = reminderId
                )
            }
        )
    }
}