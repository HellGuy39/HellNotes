package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.HomeUiEvent
import com.hellguy39.hellnotes.feature.home.HomeViewModel
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveRoute(
    navController: NavController,
    drawerState: DrawerState,
    homeViewModel: HomeViewModel,
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val selectedNoteWrappers by homeViewModel.selectedNoteWrappers.collectAsStateWithLifecycle()
    val noteStyle by homeViewModel.noteStyle.collectAsStateWithLifecycle()
    val listStyle by homeViewModel.listStyle.collectAsStateWithLifecycle()

    val uiState by archiveViewModel.uiState.collectAsStateWithLifecycle()

    ArchiveScreen(
        uiState = uiState,
        appBarSelection = ArchiveTopAppBarSelection(
            listStyle = listStyle,
            onCancelSelection = { homeViewModel.send(HomeUiEvent.CancelNoteSelection) },
            onNavigation = { scope.launch { drawerState.open() } },
            onDeleteSelected = { homeViewModel.send(HomeUiEvent.MoveToTrashSelectedNotes) },
            onSearch = { navController.navigateToSearch() },
            onChangeListStyle = { homeViewModel.send(HomeUiEvent.ToggleListStyle) },
            onUnarchiveSelected = { }
        ),
        screenSelection = ArchiveScreenSelection(
            noteSelection = NoteSelection(
                noteStyle = noteStyle,
                isSwipeable = false,
                onClick = { noteWrapper ->
                    if (selectedNoteWrappers.isNotEmpty()) {
                        homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper))
                    } else {
                        navController.navigateToNoteDetail(noteId = noteWrapper.note.id)
                    }
                },
                onLongClick = { noteWrapper -> homeViewModel.send(HomeUiEvent.SelectNote(noteWrapper)) },
                onDismiss = { _, _ -> false }
            ),
            listStyle = listStyle,
            snackbarHostState = snackbarHostState,
            selectedNoteWrappers = selectedNoteWrappers,
            noteStyle = noteStyle
        )
    )
}