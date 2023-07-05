package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.list.notes.components.NoteListTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesRoute(
    mainViewModel: MainViewModel,
    noteListViewModel: NoteListViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by noteListViewModel.uiState.collectAsStateWithLifecycle()

    NoteListScreen(
        uiState = uiState,
        appBarSelection = NoteListTopAppBarSelection(
            listStyle = ListStyle.Column,
            onCancelSelection = {
                // homeViewModel.send(HomeUiEvent.CancelNoteSelection)
            },
            onNavigation = {
                //scope.launch { drawerState.open() }
            },
            onDeleteSelected = {
                // homeViewModel.send(HomeUiEvent.MoveToTrashSelectedNotes)
            },
            onSearch = {
                //navController.navigateToSearch()
            },
            onChangeListStyle = {
                //homeViewModel.send(HomeUiEvent.ToggleListStyle)
                                },
            onArchive = {
                //homeViewModel.send(HomeUiEvent.ArchiveSelectedNotes)
            }
        ),
        screenSelection = NoteListScreenSelection(
            noteSelection = NoteSelection(
                noteStyle = NoteStyle.Outlined,
                isSwipeable = false,
                onClick = { noteWrapper ->
                    mainViewModel.openNoteEdit(noteWrapper.note.id)
//                    if (selectedNoteWrappers.isNotEmpty()) {
//                        homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper))
//                    } else {
//                        homeViewModel.send(HomeUiEvent.OpenNoteDetail(noteId = noteWrapper.note.id))
//                        //navController.navigateToNoteDetail(noteId = noteWrapper.note.id)
//                    }
                },
                onLongClick = { noteWrapper ->
                    //homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper))
                },
                onDismiss = { dismissDirection, noteWrapper ->
//                    when(
//                        if (dismissDirection == DismissDirection.StartToEnd)
//                            noteSwipesState.swipeRight
//                        else
//                            noteSwipesState.swipeLeft
//                    ) {
//                        NoteSwipe.None -> false
//                        NoteSwipe.Delete -> {
//                            homeViewModel.send(HomeUiEvent.SwipeToDeleteNote(noteWrapper))
//                            showSnack(context.getString(HellNotesStrings.Snack.NoteMovedToTrash))
//                            true
//                        }
//                        NoteSwipe.Archive -> {
//                            homeViewModel.send(HomeUiEvent.SwipeToArchiveNote(noteWrapper))
//                            showSnack(context.getString(HellNotesStrings.Snack.NoteArchived))
//                            true
//                        }
//                    }
                    false
                }
            ),
            listStyle = ListStyle.Column,
            onAddNote = {
                //navController.navigateToNoteDetail(noteId = ArgumentDefaultValues.NewNote)
            },
            snackbarHostState = snackbarHostState,
            selectedNoteWrappers = listOf()
        )
    )

}