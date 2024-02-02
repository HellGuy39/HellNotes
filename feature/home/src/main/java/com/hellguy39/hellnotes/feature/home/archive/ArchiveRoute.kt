package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@Composable
fun ArchiveRoute(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "ArchiveScreen")

    val uiState by archiveViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

    archiveViewModel.singleUiEventFlow.collectAsEventsWithLifecycle { event ->
        when (event) {
            is ArchiveSingleUiEvent.ShowSnackbar -> {
                homeState.showSnack(event.text, event.action)
            }
        }
    }

    archiveViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is ArchiveNavigationEvent.NavigateToNoteDetail -> {
                navigateToNoteDetail(event.noteId)
            }
        }
    }

    ArchiveScreen(
        uiState = uiState,
        visualState = visualState,
        onNoteClick = remember { { index -> archiveViewModel.onNoteClick(index) } },
        onNotePress = remember { { index -> archiveViewModel.onNotePress(index) } },
        onCancelSelectionClick = remember { { archiveViewModel.onCancelItemSelection() } },
        onDeleteSelectedClick = remember { { archiveViewModel.onDeleteSelectedItems() } },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onUnarchiveSelectedClick = remember { { archiveViewModel.onArchiveSelectedItems() } },
        onSearchClick = remember { { navigateToSearch() } },
        onToggleListStyle = remember { visualsViewModel::toggleListStyle },
        listStyle = visualState.listStyle,
        snackbarHostState = homeState.snackbarHostState,
    )
}
