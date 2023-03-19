package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.HomeScreenMultiActionSelection
import com.hellguy39.hellnotes.feature.home.HomeScreenVisualsSelection
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBar
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ArchiveScreen(
    navController: NavController,
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
    visualsSelection: HomeScreenVisualsSelection,
    multiActionSelection: HomeScreenMultiActionSelection
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
                    listStyle = visualsSelection.listStyle,
                    selectedNotes = multiActionSelection.selectedNotes,
                    onCancelSelection = multiActionSelection.onCancelSelection,
                    onDeleteSelected = multiActionSelection.onDeleteSelectedNotes,
                    onNavigation = { scope.launch { visualsSelection.drawerState.open() } },
                    onUnarchiveSelected = { multiActionSelection.onArchiveSelectedNotes(false) },
                    onSearch = { navController.navigateToSearch() },
                    onChangeListStyle = visualsSelection.onUpdateListStyle
                )
            )
        },
        snackbarHost = visualsSelection.snackbarHost,
        content = { paddingValues ->
            AnimatedContent(targetState = visualsSelection.listStyle) { listStyle ->

                if (uiState.notes.isEmpty()) {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.Archive),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                }

                NoteList(
                    innerPadding = paddingValues,
                    noteSelection = NoteSelection(
                        noteStyle = visualsSelection.noteStyle,
                        onClick = { note ->
                            if (multiActionSelection.selectedNotes.isEmpty()) {
                                navController.navigateToNoteDetail(note.id)
                            } else {
                                if (multiActionSelection.selectedNotes.contains(note)) {
                                    multiActionSelection.onUnselectNote(note)
                                } else {
                                    multiActionSelection.onSelectNote(note)
                                }
                            }
                        },
                        onLongClick = { note ->
                            if (multiActionSelection.selectedNotes.contains(note)) {
                                multiActionSelection.onUnselectNote(note)
                            } else {
                                multiActionSelection.onSelectNote(note)
                            }
                        },
                        onDismiss = { _, _ -> false },
                        isSwipeable = visualsSelection.noteSwipesState.enabled
                    ),
                    categories = listOf(
                        NoteCategory(notes = uiState.notes)
                    ),
                    selectedNotes = multiActionSelection.selectedNotes,
                    listStyle = listStyle,
                )
            }
        }
    )
}