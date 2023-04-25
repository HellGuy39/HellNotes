package com.hellguy39.hellnotes.feature.note_swipe_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun NoteSwipeEditRoute(
    navController: NavController,
    viewModel: NoteSwipeEditScreenViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoteSwipeEditScreen(
        onNavigationButtonClick = navController::popBackStack,
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