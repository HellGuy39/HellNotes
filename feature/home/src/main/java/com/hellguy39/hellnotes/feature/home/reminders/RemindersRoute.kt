package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersRoute(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "RemindersScreen")

    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes = actionViewModel.selectedNotes

    RemindersScreen(
        uiState = uiState,
        visualState = visualState,
        selectedNotes = selectedNotes,
        snackbarHostState = homeState.snackbarHostState,
        onNoteClick =
            remember {
                { note ->
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
                { note ->
                    if (selectedNotes.contains(note)) {
                        actionViewModel.unselectNote(note)
                    } else {
                        actionViewModel.selectNote(note)
                    }
                }
            },
        onDismissNote =
            remember {
                { direction, note ->
                    val swipeAction =
                        if (direction == DismissDirection.StartToEnd) {
                            visualState.noteSwipesState.swipeRight
                        } else {
                            visualState.noteSwipesState.swipeLeft
                        }

                    when (swipeAction) {
                        NoteSwipe.None -> false
                        NoteSwipe.Delete -> {
                            actionViewModel.deleteNote(note)
                            true
                        }

                        NoteSwipe.Archive -> {
                            actionViewModel.archiveNote(note)
                            true
                        }
                    }
                }
            },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onToggleListStyle = remember { visualsViewModel::toggleListStyle },
        onDeleteSelectedClick = remember { actionViewModel::deleteSelectedNotes },
        onCancelSelectionClick = remember { actionViewModel::cancelNoteSelection },
        onSearchClick = remember { { navigateToSearch() } },
    )
}
