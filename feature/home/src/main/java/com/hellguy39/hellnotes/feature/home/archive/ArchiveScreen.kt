package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBar
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    navController: NavController,
    noteStyle: NoteStyle,
    listStyle: ListStyle,
    drawerState: DrawerState,
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val uiState by archiveViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArchiveTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = ArchiveTopAppBarSelection(
                    selectedNotes = uiState.selectedNotes,
                    onCancelSelection = { archiveViewModel.cancelNoteSelection() },
                    onDeleteSelected = { archiveViewModel.deleteAllSelected() },
                    onNavigation = { scope.launch { drawerState.open() } },
                    onArchiveSelected = { archiveViewModel.archiveAllSelected() }
                )
            )
        },
        content = { paddingValues ->
            NoteList(
                innerPadding = paddingValues,
                noteSelection = NoteSelection(
                    noteStyle = noteStyle,
                    onClick = { note ->
                        if (uiState.selectedNotes.isEmpty()) {
                            navController.navigateToNoteDetail(note.id ?: -1)
                        } else {
                            if (uiState.selectedNotes.contains(note)) {
                                archiveViewModel.unselectNote(note)
                            } else {
                                archiveViewModel.selectNote(note)
                            }
                        }
                    },
                    onLongClick = { note ->
                        if (uiState.selectedNotes.contains(note)) {
                            archiveViewModel.unselectNote(note)
                        } else {
                            archiveViewModel.selectNote(note)
                        }
                    },
                    onDismiss = { direction, note ->
                        false
                    }
                ),
                categories = listOf(
                    NoteCategory(notes = uiState.notes)
                ),
                selectedNotes = uiState.selectedNotes,
                listStyle = listStyle,
                placeholder = {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.Archive),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                }
            )
        }
    )
}