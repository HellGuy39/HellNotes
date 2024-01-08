package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@Composable
fun ArchiveRoute(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "ArchiveScreen")

    val uiState by archiveViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes = actionViewModel.selectedNotes

    ArchiveScreen(
        uiState = uiState,
        visualState = visualState,
        selectedNotes = selectedNotes,
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
        onCancelSelectionClick = remember { actionViewModel::cancelNoteSelection },
        onDeleteSelectedClick = remember { actionViewModel::deleteSelectedNotes },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onUnarchiveSelectedClick = remember { { actionViewModel.archiveSelectedNotes(false) } },
        onSearchClick = remember { { navigateToSearch() } },
        onToggleListStyle = remember { visualsViewModel::toggleListStyle },
        listStyle = visualState.listStyle,
        snackbarHostState = homeState.snackbarHostState,
    )
}
