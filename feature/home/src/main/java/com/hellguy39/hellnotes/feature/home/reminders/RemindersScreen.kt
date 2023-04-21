package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.util.NoteSwipe
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.HomeScreenMultiActionSelection
import com.hellguy39.hellnotes.feature.home.HomeScreenVisualsSelection
import com.hellguy39.hellnotes.feature.home.reminders.components.ReminderTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.reminders.components.RemindersTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RemindersScreen(
    navController: NavController,
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    visualsSelection: HomeScreenVisualsSelection,
    multiActionSelection: HomeScreenMultiActionSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scope = rememberCoroutineScope()

    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RemindersTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = ReminderTopAppBarSelection(
                    listStyle = visualsSelection.listStyle,
                    selectedNotes = multiActionSelection.selectedNotes,
                    onNavigation = {
                        scope.launch { visualsSelection.drawerState.open() }
                    },
                    onChangeListStyle = visualsSelection.onUpdateListStyle,
                    onDeleteSelected = multiActionSelection.onDeleteSelectedNotes,
                    onCancelSelection = multiActionSelection.onCancelSelection,
                    onSearch = { navController.navigateToSearch() }
                )
            )
        },
        snackbarHost = visualsSelection.snackbarHost,
        content = { paddingValues ->
            AnimatedContent(targetState = visualsSelection.listStyle) { listStyle ->

                if (uiState.notes.isEmpty()) {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(paddingValues)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.Notifications),
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
                        onDismiss = { direction, note ->
                            val swipeAction = if (direction == DismissDirection.StartToEnd)
                                visualsSelection.noteSwipesState.swipeRight
                            else
                                visualsSelection.noteSwipesState.swipeLeft

                            when(swipeAction) {
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
                        isSwipeable = visualsSelection.noteSwipesState.enabled
                    ),
                    categories = listOf(
                        NoteCategory(notes = uiState.notes)
                    ),
                    listStyle = listStyle,
                    selectedNotes = multiActionSelection.selectedNotes,
                    listHeader = {
                        Text(
                            text = stringResource(id = HellNotesStrings.Label.Upcoming),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                )
            }
        }
    )
}