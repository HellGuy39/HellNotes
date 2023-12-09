package com.hellguy39.hellnotes.feature.notestyleedit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NoteStyleEditRoute(
    noteStyleEditViewModel: NoteStyleEditViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val uiState by noteStyleEditViewModel.uiState.collectAsStateWithLifecycle()

    BackHandler { navigateBack() }

    NoteStyleEditScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onNoteStyleChange = { noteStyle ->
            noteStyleEditViewModel.saveNoteStyle(noteStyle)
        },
    )
}
