package com.hellguy39.hellnotes.feature.notestyleedit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun NoteStyleEditRoute(
    noteStyleEditViewModel: NoteStyleEditViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    TrackScreenView(screenName = "NoteStyleEditScreen")

    BackHandler { navigateBack() }

    val uiState by noteStyleEditViewModel.uiState.collectAsStateWithLifecycle()

    NoteStyleEditScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onNoteStyleChange = { noteStyle ->
            noteStyleEditViewModel.saveNoteStyle(noteStyle)
        },
    )
}
