package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.components.list.NoteList2
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.VisualState
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelScreen(
    uiState: LabelUiState,
    visualState: VisualState,
    snackbarHostState: SnackbarHostState,
    listStyle: ListStyle,
    onNoteClick: (noteId: Long?) -> Unit,
    onNotePress: (noteId: Long?) -> Unit,
    onDismissNote: (SwipeToDismissBoxValue, noteId: Long?) -> Boolean,
    onSearchClick: () -> Unit,
    onToggleListStyle: () -> Unit,
    onArchiveSelectedClick: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
    onNavigationClick: () -> Unit,
    onRenameClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
        topBar = {
            LabelTopAppBar(
                scrollBehavior = scrollBehavior,
                uiState = uiState,
                onDeleteSelectedClick = onDeleteSelectedClick,
                onCancelSelectionClick = onCancelSelectionClick,
                onArchiveSelectedClick = onArchiveSelectedClick,
                onNavigationClick = onNavigationClick,
                listStyle = listStyle,
                onSearchClick = onSearchClick,
                onToggleListStyle = onToggleListStyle,
                onRenameClick = onRenameClick,
                onDeleteClick = onDeleteClick,
            )
        },
    ) { paddingValues ->
        if (uiState.isEmpty) {
            EmptyContentPlaceholder(
                modifier = Modifier.fillMaxSize(),
                heroIcon = UiIcon.DrawableResources(AppIcons.Folder),
                message = UiText.StringResources(AppStrings.Placeholder.Empty),
            )
        } else {
            AnimatedContent(
                targetState = visualState.listStyle,
                label = "listStyle",
            ) { listStyle ->
                NoteList2(
                    innerPadding = paddingValues,
                    noteStyle = visualState.noteStyle,
                    onClick = onNoteClick,
                    onLongClick = onNotePress,
                    onDismiss = onDismissNote,
                    isSwipeable = visualState.noteSwipesState.enabled,
                    notes = uiState.noteWrappers,
                    listStyle = listStyle,
                )
            }
        }
    }
}
