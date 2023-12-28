package com.hellguy39.hellnotes.feature.home.notelist

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.HomeScreenMultiActionSelection
import com.hellguy39.hellnotes.feature.home.HomeScreenVisualsSelection
import com.hellguy39.hellnotes.feature.home.notelist.components.NoteListTopAppBar
import com.hellguy39.hellnotes.feature.home.notelist.components.NoteListTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    noteListViewModel: NoteListViewModel = hiltViewModel(),
    visualsSelection: HomeScreenVisualsSelection,
    multiActionSelection: HomeScreenMultiActionSelection,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()

    val uiState by noteListViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                scrollBehavior = scrollBehavior,
                selectedNotes = multiActionSelection.selectedNotes,
                selection =
                    NoteListTopAppBarSelection(
                        listStyle = visualsSelection.listStyle,
                        onCancelSelection = multiActionSelection.onCancelSelection,
                        onNavigation = {
                            scope.launch { visualsSelection.drawerState.open() }
                        },
                        onDeleteSelected = multiActionSelection.onDeleteSelectedNotes,
                        onSearch = { navigateToSearch() },
                        onChangeListStyle = visualsSelection.onUpdateListStyle,
                        onArchive = { multiActionSelection.onArchiveSelectedNotes(true) },
                    ),
            )
        },
        content = { innerPadding ->

            if (uiState.isLoading) {
                return@Scaffold
            }

            AnimatedContent(
                visualsSelection.listStyle,
                label = "note_list_screen_animation",
            ) { listStyle ->
                if (uiState.pinnedNotes.isEmpty() && uiState.unpinnedNotes.isEmpty()) {
                    EmptyContentPlaceholder(
                        modifier =
                            Modifier
                                .padding(horizontal = 32.dp)
                                .padding(innerPadding)
                                .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                        message = stringResource(id = HellNotesStrings.Placeholder.Empty),
                    )
                }
                NoteList(
                    innerPadding = innerPadding,
                    noteSelection =
                        NoteSelection(
                            noteStyle = visualsSelection.noteStyle,
                            onClick = { note ->
                                if (multiActionSelection.selectedNotes.isEmpty()) {
                                    navigateToNoteDetail(note.id)
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
                            onDismiss = { direction, note ->

                                val swipeAction =
                                    if (direction == DismissDirection.StartToEnd) {
                                        visualsSelection.noteSwipesState.swipeRight
                                    } else {
                                        visualsSelection.noteSwipesState.swipeLeft
                                    }

                                when (swipeAction) {
                                    NoteSwipe.None -> false
                                    NoteSwipe.Delete -> {
                                        multiActionSelection.onDeleteNote(note)
                                        true
                                    }
                                    NoteSwipe.Archive -> {
                                        multiActionSelection.onArchiveNote(note, true)
                                        true
                                    }
                                }
                            },
                            isSwipeable = visualsSelection.noteSwipesState.enabled,
                        ),
                    categories =
                        listOf(
                            NoteCategory(
                                title = stringResource(id = HellNotesStrings.Label.Pinned),
                                notes = uiState.pinnedNotes,
                            ),
                            NoteCategory(
                                title = stringResource(id = HellNotesStrings.Label.Others),
                                notes = uiState.unpinnedNotes,
                            ),
                        ),
                    selectedNotes = multiActionSelection.selectedNotes,
                    listStyle = listStyle,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToNoteDetail(Arguments.NoteId.emptyValue)
                },
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Add),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote),
                )
            }
        },
        snackbarHost = visualsSelection.snackbarHost,
    )
}
