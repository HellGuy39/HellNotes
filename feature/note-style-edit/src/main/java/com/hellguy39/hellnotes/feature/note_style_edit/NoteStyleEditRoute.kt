package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun NoteStyleEditRoute(
    navController: NavController
) {

    val onNavigationBack: () -> Unit = {
        navController.popBackStack()
    }

    BackHandler(onBack = onNavigationBack)

    NoteStyleEditScreen(onNavigationButtonClick = onNavigationBack)

}