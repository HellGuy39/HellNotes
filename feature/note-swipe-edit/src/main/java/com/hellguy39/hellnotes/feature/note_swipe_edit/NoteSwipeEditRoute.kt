package com.hellguy39.hellnotes.feature.note_swipe_edit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe

@Composable
fun NoteSwipeEditRoute(
    viewModel: NoteSwipeEditScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    BackHandler { navigateBack() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoteSwipeEditScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        selection = NoteSwipeEditScreenSelection(
            onNoteSwipesEnabled = { enabled ->
                viewModel.send(NoteSwipeEditScreenUiEvent.EnableNoteSwipes(enabled))
            },
            onSwipeLeftActionSelected = { noteSwipe: NoteSwipe ->
                viewModel.send(NoteSwipeEditScreenUiEvent.SelectLeftAction(noteSwipe))
            },
            onSwipeRightActionSelected = { noteSwipe: NoteSwipe ->
                viewModel.send(NoteSwipeEditScreenUiEvent.SelectRightAction(noteSwipe))
            }
        )
    )
}