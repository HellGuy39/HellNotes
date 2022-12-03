package com.hellguy39.hellnotes.notes.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.Navigator
import com.hellguy39.hellnotes.notes.util.NEW_NOTE_ID
import com.hellguy39.hellnotes.notes.util.navigateToNoteDetail

@Composable
fun NoteListRoute(
    navController: NavController,
    navigator: Navigator,
    noteListViewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState by noteListViewModel.uiState.collectAsState()

    NoteListScreen(
        onSelectNoteClick = { note ->
            navController.navigateToNoteDetail(note.id)
        },
        onUpdateListTypeButtonClick = {
            noteListViewModel.updateListViewType()
        },
        onFabAddClick = {
            navController.navigateToNoteDetail(NEW_NOTE_ID)
        },
        onSettingsMenuItemClick = {
            navigator.navigateToSettings()
        },
        onAboutAppMenuItemClick = {
            navigator.navigateToAboutApp()
        },
        noteListUiState = uiState
    )
}