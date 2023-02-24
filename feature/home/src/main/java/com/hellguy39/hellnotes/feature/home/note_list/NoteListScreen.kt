package com.hellguy39.hellnotes.feature.home.note_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.note_list.components.ListConfiguration
import com.hellguy39.hellnotes.feature.home.note_list.components.ListConfigurationSelection
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBar
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.util.HomeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteListScreen(
    navController: NavController,
    drawerState: DrawerState,
    noteListViewModel: NoteListViewModel = hiltViewModel(),
    listStyle: ListStyle,
    onChangeListStyle: () -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scope = rememberCoroutineScope()
    val sortingMenuState = rememberDropdownMenuState()
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by noteListViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                scrollBehavior = scrollBehavior,
                selectedNotes = uiState.selectedNotes,
                selection = NoteListTopAppBarSelection(
                    listStyle = listStyle,
                    onCancelSelection = { noteListViewModel.cancelNoteSelection() },
                    onNavigation = { scope.launch { drawerState.open() } },
                    onDeleteSelected = {
                        //showOnDeleteNotesSnack(HomeScreen.NoteList)
                        noteListViewModel.deleteAllSelected()
                    },
                    onSearch = { navController.navigateToSearch() },
                    onChangeListStyle = onChangeListStyle,
                    onArchive = { noteListViewModel.archiveAllSelected() }
                )
            )
        },
        content = { innerPadding ->

            if (uiState.isLoading) {
                return@Scaffold
            }

            AnimatedContent(listStyle) { listStyle ->
                NoteList(
                    innerPadding = innerPadding,
                    noteSelection = NoteSelection(
                        onClick = { note ->
                            if (uiState.selectedNotes.isEmpty()) {
                                navController.navigateToNoteDetail(note.id)
                            } else {
                                if (uiState.selectedNotes.contains(note)) {
                                    noteListViewModel.unselectNote(note)
                                } else {
                                    noteListViewModel.selectNote(note)
                                }
                            }
                        },
                        onLongClick = { note ->
                            if (uiState.selectedNotes.contains(note)) {
                                noteListViewModel.unselectNote(note)
                            } else {
                                noteListViewModel.selectNote(note)
                            }
                        },
                        onDismiss = { direction, note ->
                            false
                        }
                    ),
                    categories = listOf(
                        NoteCategory(
                            title = stringResource(id = HellNotesStrings.Label.Pinned),
                            notes = uiState.pinnedNotes,
                        ),
                        NoteCategory(
                            title = stringResource(id = HellNotesStrings.Label.Others),
                            notes = uiState.unpinnedNotes,
                        )
                    ),
                    selectedNotes = uiState.selectedNotes,
                    listStyle = listStyle,
                    listHeader = {
                        ListConfiguration(
                            selection = ListConfigurationSelection(
                                sorting = uiState.sorting,
                                onSortingSelected = { sorting ->
                                    noteListViewModel.updateSorting(sorting)
                                }
                            ),
                            menuState = sortingMenuState
                        )
                    },
                    placeholder = {
                        EmptyContentPlaceholder(
                            paddingValues = innerPadding,
                            heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                            message = stringResource(id = HellNotesStrings.Text.Empty)
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigateToNoteDetail(ArgumentDefaultValues.NewNote) }
            ) {
                Icon(
                    painterResource(id = HellNotesIcons.Add),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(12.dp),
                    action = {
                        TextButton(
                            onClick = {
                                data.performAction()
                            },
                        ) {
                            Text(
                                text = data.visuals.actionLabel ?: "",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.inversePrimary
                                )
                            )
                        }
                    },
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    )
}