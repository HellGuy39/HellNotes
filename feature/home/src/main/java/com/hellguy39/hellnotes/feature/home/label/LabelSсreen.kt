package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
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
import com.hellguy39.hellnotes.core.model.util.NoteSwipe
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
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBar
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBarSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LabelScreen(
    navController: NavController,
    labelViewModel: LabelViewModel = hiltViewModel(),
    visualsSelection: HomeScreenVisualsSelection,
    multiActionSelection: HomeScreenMultiActionSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scope = rememberCoroutineScope()

    val uiState by labelViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            AnimatedContent(targetState = visualsSelection.listStyle) { listStyle ->
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
                        NoteCategory(
                            notes = uiState.notes
                        )
                    ),
                    selectedNotes = multiActionSelection.selectedNotes,
                    listStyle = listStyle,
                    placeholder = {
                        EmptyContentPlaceholder(
                            heroIcon = painterResource(id = HellNotesIcons.Label),
                            message = stringResource(id = HellNotesStrings.Text.Empty)
                        )
                    }
                )
            }
        },
        topBar = {
            LabelTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = LabelTopAppBarSelection(
                    selectedNotes = multiActionSelection.selectedNotes,
                    onDeleteSelected = multiActionSelection.onDeleteSelectedNotes,
                    onCancelSelection = multiActionSelection.onCancelSelection,
                    onArchiveSelected = { multiActionSelection.onArchiveSelectedNotes(true) },
                    onNavigation = {
                        scope.launch {
                            visualsSelection.drawerState.open()
                        }
                    },
                    listStyle = visualsSelection.listStyle,
                    onSearch = { navController.navigateToSearch() },
                    onChangeListStyle = visualsSelection.onUpdateListStyle
                ),
                label = uiState.label
            )
        }
    )
}