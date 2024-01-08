package com.hellguy39.hellnotes.feature.home.notes

import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.analytics.LocalAnalytics
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.analytics.buttonClick
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.feature.home.ActionSingleEvent
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

private const val SCREEN_NAME = "NotesScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesRoute(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    notesViewModel: NotesViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = SCREEN_NAME)

    val analyticsLogger = LocalAnalytics.current

    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes = actionViewModel.selectedNotes

    actionViewModel.actionSingleEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is ActionSingleEvent.ShowSnackbar -> {
                homeState.showSnack(event.text, actionViewModel::undo)
            }
        }
    }

    NotesScreen(
        uiState = uiState,
        visualState = visualState,
        selectedNotes = selectedNotes,
        onAddNewNoteClick =
            remember {
                {
                    analyticsLogger.buttonClick("NotesScreen", "fab_add_new_note")
                    navigateToNoteDetail(Arguments.NoteId.emptyValue)
                }
            },
        onNoteClick =
            remember {
                { note: Note ->
                    if (selectedNotes.isEmpty()) {
                        navigateToNoteDetail(note.id)
                    } else {
                        if (selectedNotes.contains(note)) {
                            actionViewModel.unselectNote(note)
                        } else {
                            actionViewModel.selectNote(note)
                        }
                    }
                }
            },
        onNotePress =
            remember {
                { note: Note ->
                    if (selectedNotes.contains(note)) {
                        actionViewModel.unselectNote(note)
                    } else {
                        actionViewModel.selectNote(note)
                    }
                }
            },
        onDismissNote =
            remember {
                { direction: DismissDirection, note: Note ->
                    val swipeAction =
                        if (direction == DismissDirection.StartToEnd) {
                            visualState.noteSwipesState.swipeRight
                        } else {
                            visualState.noteSwipesState.swipeLeft
                        }

                    when (swipeAction) {
                        NoteSwipe.None -> false
                        NoteSwipe.Delete -> {
                            actionViewModel.deleteNote(note = note)
                            true
                        }

                        NoteSwipe.Archive -> {
                            actionViewModel.archiveNote(note = note, isArchived = true)
                            true
                        }
                    }
                }
            },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onDeleteSelectedNotesClick = remember { { actionViewModel.deleteSelectedNotes() } },
        onToggleListStyle = remember { { visualsViewModel.toggleListStyle() } },
        onSearchbarClick = remember { { navigateToSearch() } },
        onCancelNoteSelection = remember { { actionViewModel.cancelNoteSelection() } },
        onArchiveSelectedNotesClick =
            remember {
                {
                    actionViewModel.archiveSelectedNotes(true)
                }
            },
        snackbarHostState = homeState.snackbarHostState,
    )
}
