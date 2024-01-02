package com.hellguy39.hellnotes.feature.home.notes

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.analytics.LocalAnalytics
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.analytics.buttonClick
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.state.HomeState
import com.hellguy39.hellnotes.feature.home.ActionSingleEvent
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.VisualsViewModel
import com.hellguy39.hellnotes.feature.home.notes.components.NoteListTopAppBar
import com.hellguy39.hellnotes.feature.home.notes.components.NoteListTopAppBarSelection

private const val SCREEN_NAME = "NotesScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    notesViewModel: NotesViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = SCREEN_NAME)

    val analyticsLogger = LocalAnalytics.current

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes by actionViewModel.selectedNotes.collectAsStateWithLifecycle()

    actionViewModel.actionSingleEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is ActionSingleEvent.ShowSnackbar -> {
                homeState.showSnack(event.text, actionViewModel::undo)
            }
        }
    }

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                scrollBehavior = scrollBehavior,
                selectedNotes = selectedNotes,
                selection =
                    NoteListTopAppBarSelection(
                        listStyle = visualState.listStyle,
                        onCancelSelection = actionViewModel::cancelNoteSelection,
                        onNavigation = { homeState.openDrawer() },
                        onDeleteSelected = actionViewModel::deleteSelectedNotes,
                        onSearch = { navigateToSearch() },
                        onChangeListStyle = visualsViewModel::toggleListStyle,
                        onArchive = { actionViewModel.archiveSelectedNotes(true) },
                    ),
            )
        },
        content = { innerPadding ->
            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.NoteAdd),
                    message = UiText.StringResources(AppStrings.Placeholder.Empty),
                )
            } else {
                AnimatedContent(
                    visualState.listStyle,
                    label = "note_list_screen_animation",
                ) { listStyle ->
                    NoteList(
                        innerPadding = innerPadding,
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
                        onDismiss = { direction, note ->

                            val swipeAction =
                                if (direction == DismissDirection.StartToEnd) {
                                    visualState.noteSwipesState.swipeRight
                                } else {
                                    visualState.noteSwipesState.swipeLeft
                                }

                            when (swipeAction) {
                                NoteSwipe.None -> false
                                NoteSwipe.Delete -> {
                                    actionViewModel.deleteNote(note = note)
                                    true
                                }
                                NoteSwipe.Archive -> {
                                    actionViewModel.archiveNote(note = note, isArchived = true)
                                    true
                                }
                            }
                        },
                        isSwipeable = visualState.noteSwipesState.enabled,
                        categories =
                            listOf(
                                NoteCategory(
                                    title = stringResource(id = AppStrings.Label.Pinned),
                                    notes = uiState.pinnedNotes,
                                ),
                                NoteCategory(
                                    title = stringResource(id = AppStrings.Label.Others),
                                    notes = uiState.unpinnedNotes,
                                ),
                            ),
                        selectedNotes = selectedNotes,
                        listStyle = listStyle,
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    analyticsLogger.buttonClick(SCREEN_NAME, "fab_add_new_note")
                    navigateToNoteDetail(Arguments.NoteId.emptyValue)
                },
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.Add),
                    contentDescription = stringResource(id = AppStrings.ContentDescription.AddNote),
                )
            }
        },
        snackbarHost = { CustomSnackbarHost(state = homeState.snackbarHostState) },
    )
}
