package com.hellguy39.hellnotes.feature.note_swipe_edit

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun NoteSwipeEditRoute(
    navController: NavController
) {
    val onNavigationBack: () -> Unit = {
        navController.popBackStack()
    }

    BackHandler(onBack = onNavigationBack)

    NoteSwipeEditScreen(onNavigationButtonClick = onNavigationBack)
}