package com.hellguy39.hellnotes.feature.label_selection.navigation

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
import com.hellguy39.hellnotes.feature.label_selection.LabelSelectionRoute

fun NavGraphBuilder.labelSelectionScreen(
    appState: HellNotesAppState
) {
    composable(
        route = Screen.LabelSelection.withArgKeys(ArgumentKeys.NoteId),
        arguments = listOf(
            navArgument(name = ArgumentKeys.NoteId) {
                type = NavType.LongType
                defaultValue = ArgumentDefaultValues.NewNote
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
        LabelSelectionRoute(
            navigateBack = { appState.navigateUp() }
        )
    }
}
