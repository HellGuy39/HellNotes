package com.hellguy39.hellnotes.feature.home.list.reminders

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
import com.hellguy39.hellnotes.feature.home.list.reminders.components.ReminderTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersRoute(
    remindersViewModel: RemindersViewModel = hiltViewModel(),
) {
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

//    val selectedNoteWrappers by homeViewModel.selectedNoteWrappers.collectAsStateWithLifecycle()
//    val noteStyle by homeViewModel.noteStyle.collectAsStateWithLifecycle()
//    val listStyle by homeViewModel.listStyle.collectAsStateWithLifecycle()
//    val noteSwipesState by homeViewModel.noteSwipesState.collectAsStateWithLifecycle()

    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()

//    fun showSnack(message: String) {
//        snackbarHostState.showDismissableSnackbar(
//            scope = scope,
//            message = message,
//            actionLabel = context.getString(HellNotesStrings.Button.Undo),
//            onActionPerformed = { homeViewModel.send(HomeUiEvent.Undo) },
//            duration = SnackbarDuration.Long
//        )
//    }

    RemindersScreen(
        uiState = uiState,
        appBarSelection = ReminderTopAppBarSelection(
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
        ),
        screenSelection = RemindersScreenSelection(
            listStyle = ListStyle.Column,
            noteStyle = NoteStyle.Outlined,
            selectedNoteWrappers = listOf(),
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
            snackbarHostState = snackbarHostState
        )
    )
}