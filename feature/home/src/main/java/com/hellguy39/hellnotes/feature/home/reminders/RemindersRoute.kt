package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
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
) {
    TrackScreenView(screenName = "RemindersScreen")

    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

    remindersViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is RemindersNavigationEvent.NavigateToNoteDetail -> {
                navigateToNoteDetail(event.noteId)
            }
        }
    }

    RemindersScreen(
        uiState = uiState,
        visualState = visualState,
        snackbarHostState = homeState.snackbarHostState,
        onNoteClick = remember { { index -> remindersViewModel.onNoteClick(index) } },
        onNotePress = remember { { index -> remindersViewModel.onNotePress(index) } },
        onDismissNote =
            remember {
                { direction, index ->
                    val swipeAction = visualsViewModel.calculateSwipeAction(direction)
                    remindersViewModel.onNoteDismiss(swipeAction, index)
                    visualsViewModel.calculateSwipeResult(swipeAction)
                }
            },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onToggleListStyle = remember { visualsViewModel::toggleListStyle },
        onDeleteSelectedClick = remember { { remindersViewModel.onDeleteSelectedItems() } },
        onCancelSelectionClick = remember { { remindersViewModel.onCancelItemSelection() } },
        onSearchClick = remember { { navigateToSearch() } },
    )
}
