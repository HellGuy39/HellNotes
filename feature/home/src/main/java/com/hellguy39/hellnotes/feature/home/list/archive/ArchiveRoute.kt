package com.hellguy39.hellnotes.feature.home.list.archive

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.feature.home.list.archive.components.ArchiveTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveRoute(
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

//    val selectedNoteWrappers by homeViewModel.selectedNoteWrappers.collectAsStateWithLifecycle()
//    val noteStyle by homeViewModel.noteStyle.collectAsStateWithLifecycle()
//    val listStyle by homeViewModel.listStyle.collectAsStateWithLifecycle()

    val uiState by archiveViewModel.uiState.collectAsStateWithLifecycle()

    ArchiveScreen(
        uiState = uiState,
        appBarSelection = ArchiveTopAppBarSelection(
            listStyle = ListStyle.Column,
            onCancelSelection = {
                //homeViewModel.send(HomeUiEvent.CancelNoteSelection)
                                },
            onNavigation = {
                //scope.launch { drawerState.open() }
                           },
            onDeleteSelected = {
                //homeViewModel.send(HomeUiEvent.MoveToTrashSelectedNotes)
                               },
            onSearch = {
                //navController.navigateToSearch()
                       },
            onChangeListStyle = {
                //homeViewModel.send(HomeUiEvent.ToggleListStyle)
                                },
            onUnarchiveSelected = { }
        ),
        screenSelection = ArchiveScreenSelection(
            noteSelection = NoteSelection(
                noteStyle = NoteStyle.Outlined,
                isSwipeable = false,
                onClick = { noteWrapper ->
//                    if (selectedNoteWrappers.isNotEmpty()) {
//                        homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper))
//                    } else {
//                        navController.navigateToNoteDetail(noteId = noteWrapper.note.id)
//                    }
                },
                onLongClick = { noteWrapper ->
                    //homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper))
                              },
                onDismiss = { _, _ -> false }
            ),
            listStyle = ListStyle.Column,
            snackbarHostState = snackbarHostState,
            selectedNoteWrappers = listOf(),
            noteStyle = NoteStyle.Outlined
        )
    )
}