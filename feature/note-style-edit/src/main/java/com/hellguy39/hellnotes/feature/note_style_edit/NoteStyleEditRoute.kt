package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun NoteStyleEditRoute(
    navController: NavController,
    noteStyleEditViewModel: NoteStyleEditViewModel = hiltViewModel()
) {
    val uiState by noteStyleEditViewModel.uiState.collectAsStateWithLifecycle()

    BackHandler { navController.popBackStack() }

    NoteStyleEditScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        onNoteStyleChange = { noteStyle ->
            noteStyleEditViewModel.saveNoteStyle(noteStyle)
        }
    )
}