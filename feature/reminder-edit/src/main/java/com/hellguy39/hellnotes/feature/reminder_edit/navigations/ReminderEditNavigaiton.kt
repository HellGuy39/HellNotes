package com.hellguy39.hellnotes.feature.reminder_edit.navigations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.AnimDuration
import com.hellguy39.hellnotes.feature.reminder_edit.ReminderEditRoute

fun NavGraphBuilder.reminderEditScreen(
    appState: HellNotesAppState
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
            when(initialState.destination.route) {
                Screen.NoteDetail.withArgKeys(ArgumentKeys.NoteId) -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(AnimDuration.medium)
                    )
                }
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when(targetState.destination.route) {
                Screen.NoteDetail.withArgKeys(ArgumentKeys.NoteId) -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(AnimDuration.fast)
                    )
                }
                else -> null
            }
        }
    ) {
        ReminderEditRoute(
            navigateBack = { appState.navigateUp() }
        )
    }
}