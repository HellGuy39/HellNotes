package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.state.HomeState
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.VisualsViewModel
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBar
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    archiveViewModel: ArchiveViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "ArchiveScreen")

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val uiState by archiveViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes by actionViewModel.selectedNotes.collectAsStateWithLifecycle()

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArchiveTopAppBar(
                scrollBehavior = scrollBehavior,
                selection =
                    ArchiveTopAppBarSelection(
                        listStyle = visualState.listStyle,
                        selectedNotes = selectedNotes,
                        onCancelSelection = actionViewModel::cancelNoteSelection,
                        onDeleteSelected = actionViewModel::deleteSelectedNotes,
                        onNavigation = { homeState.openDrawer() },
                        onUnarchiveSelected = { actionViewModel.archiveSelectedNotes(false) },
                        onSearch = { navigateToSearch() },
                        onChangeListStyle = visualsViewModel::toggleListStyle,
                    ),
            )
        },
        snackbarHost = { CustomSnackbarHost(state = homeState.snackbarHostState) },
        content = { paddingValues ->
            AnimatedContent(
                targetState = visualState.listStyle,
                label = "listStyle",
            ) { listStyle ->

                if (uiState.notes.isEmpty()) {
                    EmptyContentPlaceholder(
                        modifier =
                            Modifier
                                .padding(horizontal = 32.dp)
                                .padding(paddingValues)
                                .fillMaxSize(),
                        heroIcon = painterResource(id = AppIcons.Archive),
                        message = stringResource(id = AppStrings.Placeholder.Empty),
                    )
                }

                NoteList(
                    innerPadding = paddingValues,
                    noteSelection =
                        NoteSelection(
                            noteStyle = visualState.noteStyle,
                            onClick = { note ->
                                if (selectedNotes.isEmpty()) {
                                    navigateToNoteDetail(note.id)
                                } else {
                                    if (selectedNotes.contains(note)) {
                                        actionViewModel.unselectNote(note)
                                    } else {
                                        actionViewModel.selectNote(note)
                                    }
                                }
                            },
                            onLongClick = { note ->
                                if (selectedNotes.contains(note)) {
                                    actionViewModel.unselectNote(note)
                                } else {
                                    actionViewModel.selectNote(note)
                                }
                            },
                            onDismiss = { _, _ -> false },
                            isSwipeable = visualState.noteSwipesState.enabled,
                        ),
                    categories =
                        listOf(
                            NoteCategory(notes = uiState.notes),
                        ),
                    selectedNotes = selectedNotes,
                    listStyle = listStyle,
                )
            }
        },
    )
}
