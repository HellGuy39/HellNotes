package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipe
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel
import com.hellguy39.hellnotes.feature.home.reminders.components.ReminderTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.reminders.components.RemindersTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    remindersViewModel: RemindersViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "RemindersScreen")

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val uiState by remindersViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes by actionViewModel.selectedNotes.collectAsStateWithLifecycle()

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RemindersTopAppBar(
                scrollBehavior = scrollBehavior,
                selection =
                    ReminderTopAppBarSelection(
                        listStyle = visualState.listStyle,
                        selectedNotes = selectedNotes,
                        onNavigation = { homeState.openDrawer() },
                        onChangeListStyle = visualsViewModel::toggleListStyle,
                        onDeleteSelected = actionViewModel::deleteSelectedNotes,
                        onCancelSelection = actionViewModel::cancelNoteSelection,
                        onSearch = { navigateToSearch() },
                    ),
            )
        },
        snackbarHost = { CustomSnackbarHost(state = homeState.snackbarHostState) },
        content = { paddingValues ->
            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.Notifications),
                    message = UiText.StringResources(AppStrings.Placeholder.Empty),
                )
            } else {
                AnimatedContent(
                    targetState = visualState.listStyle,
                    label = "listStyle",
                ) { listStyle ->
                    NoteList(
                        innerPadding = paddingValues,
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
                                    actionViewModel.deleteNote(note)
                                    true
                                }

                                NoteSwipe.Archive -> {
                                    actionViewModel.archiveNote(note)
                                    true
                                }
                            }
                        },
                        isSwipeable = visualState.noteSwipesState.enabled,
                        categories =
                            listOf(
                                NoteCategory(notes = uiState.notes),
                            ),
                        listStyle = listStyle,
                        selectedNotes = selectedNotes,
                        listHeader = {
                            Text(
                                text = stringResource(id = AppStrings.Label.Upcoming),
                                modifier =
                                    Modifier
                                        .padding(horizontal = Spaces.medium, vertical = Spaces.small),
                                style = MaterialTheme.typography.titleSmall,
                            )
                        },
                    )
                }
            }
        },
    )
}
