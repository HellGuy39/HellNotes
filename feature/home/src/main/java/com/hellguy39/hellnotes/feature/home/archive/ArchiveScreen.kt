package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.VisualState
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    uiState: ArchiveUiState,
    visualState: VisualState,
    snackbarHostState: SnackbarHostState,
    listStyle: ListStyle,
    onNoteClick: (index: Int) -> Unit,
    onNotePress: (index: Int) -> Unit,
    onSearchClick: () -> Unit,
    onToggleListStyle: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
    onUnarchiveSelectedClick: () -> Unit,
    onNavigationClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArchiveTopAppBar(
                scrollBehavior = scrollBehavior,
                listStyle = listStyle,
                uiState = uiState,
                onCancelSelectionClick = onCancelSelectionClick,
                onDeleteSelectedClick = onDeleteSelectedClick,
                onNavigationClick = onNavigationClick,
                onUnarchiveSelectedClick = onUnarchiveSelectedClick,
                onSearchClick = onSearchClick,
                onToggleListStyle = onToggleListStyle,
            )
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
        content = { paddingValues ->
            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.Archive),
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
                        isSwipeable = visualState.noteSwipesState.enabled,
                        notes = uiState.selectableNoteWrappers,
                        listStyle = listStyle,
                    )
                }
            }
        },
    )
}
