package com.hellguy39.hellnotes.feature.note_style_edit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun NoteStyleEditRoute(
    noteStyleEditViewModel: NoteStyleEditViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by noteStyleEditViewModel.uiState.collectAsStateWithLifecycle()

    BackHandler { navigateBack() }

    NoteStyleEditScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onNoteStyleChange = { noteStyle ->
            noteStyleEditViewModel.saveNoteStyle(noteStyle)
        }
    )
}