package com.hellguy39.hellnotes.notes.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
) {

    val note by noteDetailViewModel.note.collectAsState()

    NoteDetailScreen(
        onNavigationButtonClick = {
            noteDetailViewModel.saveNote()
            navController.popBackStack()
        },
        note = note,
        onPinButtonClick = { isPinned -> noteDetailViewModel.updateIsPinned(isPinned) },
        onLabelButtonClick = {},
        onDeleteButtonClick = {
            noteDetailViewModel.deleteNote()
            navController.popBackStack()
        },
        onTitleTextChanged = { newText -> noteDetailViewModel.updateNoteTitle(newText) },
        onNoteTextChanged = { newText -> noteDetailViewModel.updateNoteContent(newText) }
    )
}