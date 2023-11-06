package com.hellguy39.hellnotes.feature.reminder_edit.navigations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.reminder_edit.ReminderEditRoute

fun NavGraphBuilder.reminderEditScreen(
    navController: NavController
) {
    composable(
        route = Screen.ReminderEdit.withArgKeys(ArgumentKeys.NoteId, ArgumentKeys.ReminderId),
        arguments = listOf(
            navArgument(name = ArgumentKeys.NoteId) {
                type = NavType.LongType
                defaultValue = ArgumentDefaultValues.NewNote
            },
            navArgument(name = ArgumentKeys.ReminderId) {
                type = NavType.LongType
                defaultValue = ArgumentDefaultValues.NewReminder
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
        ReminderEditRoute(navController)
    }
}