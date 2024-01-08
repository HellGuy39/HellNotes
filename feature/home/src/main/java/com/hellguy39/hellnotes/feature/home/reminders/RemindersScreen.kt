package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.home.VisualState
import com.hellguy39.hellnotes.feature.home.reminders.components.RemindersTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    uiState: RemindersUiState,
    visualState: VisualState,
    selectedNotes: SnapshotStateList<Note>,
    snackbarHostState: SnackbarHostState,
    onNoteClick: (note: Note) -> Unit,
    onNotePress: (note: Note) -> Unit,
    onDismissNote: (direction: DismissDirection, note: Note) -> Boolean,
    onNavigationClick: () -> Unit,
    onToggleListStyle: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RemindersTopAppBar(
                scrollBehavior = scrollBehavior,
                listStyle = visualState.listStyle,
                selectedNotes = selectedNotes,
                onNavigationClick = onNavigationClick,
                onToggleListStyle = onToggleListStyle,
                onDeleteSelectedClick = onDeleteSelectedClick,
                onCancelSelectionClick = onCancelSelectionClick,
                onSearchClick = onSearchClick,
            )
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
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
                        onClick = onNoteClick,
                        onLongClick = onNotePress,
                        onDismiss = onDismissNote,
                        isSwipeable = visualState.noteSwipesState.enabled,
                        categories = uiState.noteCategories,
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
