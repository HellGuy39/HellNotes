package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun NoteStyleEditRoute(
    navController: NavController,
    noteStyleEditViewModel: NoteStyleEditViewModel = hiltViewModel()
) {

    val onNavigationBack: () -> Unit = {
        navController.popBackStack()
    }

    val uiState by noteStyleEditViewModel.uiState.collectAsStateWithLifecycle()

    BackHandler(onBack = onNavigationBack)

    NoteStyleEditScreen(
        onNavigationButtonClick = onNavigationBack,
        uiState = uiState,
        onNoteStyleChange = { noteStyle ->
            noteStyleEditViewModel.saveNoteStyle(noteStyle)
        }
    )

}