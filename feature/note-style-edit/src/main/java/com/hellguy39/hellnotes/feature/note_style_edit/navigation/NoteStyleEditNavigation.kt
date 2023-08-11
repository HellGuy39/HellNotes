package com.hellguy39.hellnotes.feature.note_style_edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.note_style_edit.NoteStyleEditRoute

fun NavGraphBuilder.noteStyleEditScreen(
    navController: NavController
) {
    composable(
        route = Screen.NoteStyleEdit.route,
    ) {
        NoteStyleEditRoute(navController)
    }
}