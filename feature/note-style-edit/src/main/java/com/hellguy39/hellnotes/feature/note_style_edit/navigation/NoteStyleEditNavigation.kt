package com.hellguy39.hellnotes.feature.note_style_edit.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.AnimDuration
import com.hellguy39.hellnotes.feature.note_style_edit.NoteStyleEditRoute

fun NavGraphBuilder.noteStyleEditScreen(
    appState: HellNotesAppState
) {
    composable(
        route = Screen.NoteStyleEdit.route,
        enterTransition = {
            when(initialState.destination.route) {
                Screen.Settings.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(AnimDuration.medium)
                    )
                }
                else -> null
            }
        },
        exitTransition = {
            when(targetState.destination.route) {
                Screen.Settings.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(AnimDuration.fast)
                    )
                }
                else -> null
            }
        },
    ) {
        NoteStyleEditRoute(
            navigateBack = { appState.navigateUp() }
        )
    }
}