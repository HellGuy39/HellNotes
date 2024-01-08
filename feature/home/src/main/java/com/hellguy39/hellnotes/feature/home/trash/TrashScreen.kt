package com.hellguy39.hellnotes.feature.home.trash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.ui.components.cards.TipCard
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.VisualState
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    uiState: TrashUiState,
    visualState: VisualState,
    selectedNotes: SnapshotStateList<Note>,
    onNoteClick: (note: Note) -> Unit,
    onNotePress: (note: Note) -> Unit,
    onNavigationClick: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onRestoreSelectedClick: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
    onEmptyTrashClick: () -> Unit,
    onCloseTrashTip: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrashTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationClick = onNavigationClick,
                selectedNotes = selectedNotes,
                onCancelSelectionClick = onCancelSelectionClick,
                onRestoreSelectedClick = onRestoreSelectedClick,
                onDeleteSelectedClick = onDeleteSelectedClick,
                onEmptyTrashClick = onEmptyTrashClick,
            )
        },
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
        content = { paddingValues ->
            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.Delete),
                    message = UiText.StringResources(AppStrings.Placeholder.NoNotesInTrash),
                )
            } else {
                NoteList(
                    innerPadding = paddingValues,
                    noteStyle = visualState.noteStyle,
                    onClick = onNoteClick,
                    onLongClick = onNotePress,
                    onDismiss = remember { { _, _ -> false } },
                    isSwipeable = visualState.noteSwipesState.enabled,
                    categories = uiState.noteCategories,
                    selectedNotes = selectedNotes,
                    listStyle = visualState.listStyle,
                    listHeader = {
                        TipCard(
                            isVisible = !uiState.trashTipCompleted,
                            message = stringResource(id = AppStrings.Tip.AutoDeleteTrash),
                            onClose = onCloseTrashTip,
                        )
                    },
                )
            }
        },
    )
}
