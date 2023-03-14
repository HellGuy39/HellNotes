package com.hellguy39.hellnotes.feature.note_swipe_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.NoteSwipe
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun NoteSwipeEditRoute(
    navController: NavController,
    viewModel: NoteSwipeEditScreenViewModel = hiltViewModel()
) {
    val onNavigationBack: () -> Unit = {
        navController.popBackStack()
    }

    BackHandler(onBack = onNavigationBack)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NoteSwipeEditScreen(
        onNavigationButtonClick = onNavigationBack,
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