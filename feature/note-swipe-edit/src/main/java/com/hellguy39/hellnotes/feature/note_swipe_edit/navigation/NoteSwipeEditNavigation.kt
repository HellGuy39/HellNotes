package com.hellguy39.hellnotes.feature.note_swipe_edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.note_swipe_edit.NoteSwipeEditRoute

fun NavGraphBuilder.noteSwipeEditScreen(
    navController: NavController
) {
    composable(
        route = Screen.NoteSwipeEdit.route,
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
        NoteSwipeEditRoute(navController)
    }
}