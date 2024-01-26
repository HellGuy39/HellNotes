package com.hellguy39.hellnotes.feature.home.notes

import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.analytics.LocalAnalytics
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.analytics.buttonClick
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.core.ui.wrapper.PartitionElementPositionInfo
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
) {
    TrackScreenView(screenName = SCREEN_NAME)

    val analyticsLogger = LocalAnalytics.current

    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

//    notesViewModel.singleEvents.collectAsEventsWithLifecycle { event ->
//        when (event) {
//            is NotesSingleEvent.ShowSnackbar -> {
//                homeState.showSnack(event.text, event.action)
//            }
//            else -> Unit
//        }
//    }

    notesViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is NotesNavigationEvent.NavigateToNoteDetail -> {
                navigateToNoteDetail(event.noteId)
            }
        }
    }

    NotesScreen(
        uiState = uiState,
        visualState = visualState,
        onAddNewNoteClick =
            remember {
                {
                    analyticsLogger.buttonClick(SCREEN_NAME, "fab_add_new_note")
                    navigateToNoteDetail(Arguments.NoteId.emptyValue)
                }
            },
        onNoteClick =
            remember {
                { positionInfo: PartitionElementPositionInfo ->
                    notesViewModel.onNoteClick(positionInfo)
                }
            },
        onNotePress =
            remember {
                { positionInfo: PartitionElementPositionInfo ->
                    notesViewModel.onNotePress(positionInfo)
                }
            },
        onDismissNote =
            remember {
                { direction: DismissDirection, positionInfo: PartitionElementPositionInfo ->
                    val swipeAction = visualsViewModel.calculateSwipeAction(direction)
                    notesViewModel.onNoteDismiss(swipeAction, positionInfo)
                    visualsViewModel.calculateSwipeResult(swipeAction)
                }
            },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onDeleteSelectedNotesClick = remember { { notesViewModel.onDeleteSelectedItems() } },
        onToggleListStyle = remember { { visualsViewModel.toggleListStyle() } },
        onSearchbarClick = remember { { navigateToSearch() } },
        onCancelNoteSelection = remember { { notesViewModel.onCancelItemSelection() } },
        onArchiveSelectedNotesClick = remember { { notesViewModel.onArchiveSelectedItems() } },
        snackbarHostState = homeState.snackbarHostState,
    )
}
