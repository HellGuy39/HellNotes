package com.hellguy39.hellnotes.feature.noteswipeedit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun NoteSwipeEditRoute(
    viewModel: NoteSwipeEditScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    TrackScreenView(screenName = "NoteSwipeEditScreen")

    BackHandler { navigateBack() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoteSwipeEditScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        selection =
            NoteSwipeEditScreenSelection(
                onNoteSwipesEnabled = { enabled ->
                    viewModel.send(NoteSwipeEditScreenUiEvent.EnableNoteSwipes(enabled))
                },
                onSwipeLeftActionSelected = { noteSwipe: NoteSwipe ->
                    viewModel.send(NoteSwipeEditScreenUiEvent.SelectLeftAction(noteSwipe))
                },
                onSwipeRightActionSelected = { noteSwipe: NoteSwipe ->
                    viewModel.send(NoteSwipeEditScreenUiEvent.SelectRightAction(noteSwipe))
                },
            ),
    )
}
