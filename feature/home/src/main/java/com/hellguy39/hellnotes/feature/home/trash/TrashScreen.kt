package com.hellguy39.hellnotes.feature.home.trash

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
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.cards.TipCard
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.state.HomeState
import com.hellguy39.hellnotes.feature.home.ActionViewModel
import com.hellguy39.hellnotes.feature.home.VisualsViewModel
import com.hellguy39.hellnotes.feature.home.trash.components.TrashDropdownMenuSelection
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBar
import com.hellguy39.hellnotes.feature.home.trash.components.TrashTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    homeState: HomeState,
    trashViewModel: TrashViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
    actionViewModel: ActionViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "TrashScreen")

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val restoreDialogState = rememberDialogState()
    val emptyTrashDialogState = rememberDialogState()

    val uiState by trashViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()
    val selectedNotes by actionViewModel.selectedNotes.collectAsStateWithLifecycle()

    CustomDialog(
        state = emptyTrashDialogState,
        heroIcon = painterResource(id = AppIcons.Delete),
        title = stringResource(id = AppStrings.Title.EmptyTrash),
        message = stringResource(id = AppStrings.Supporting.EmptyTrash),
        onCancel = {
            emptyTrashDialogState.dismiss()
        },
        onAccept = {
            trashViewModel.emptyTrash()
            emptyTrashDialogState.dismiss()
        },
    )

    CustomDialog(
        state = restoreDialogState,
        heroIcon = painterResource(id = AppIcons.RestoreFromTrash),
        title = stringResource(id = AppStrings.Title.RestoreThisNote),
        message = stringResource(id = AppStrings.Supporting.RestoreNote),
        onClose = {
            trashViewModel.clearSelectedNote()
            restoreDialogState.dismiss()
        },
        onCancel = {
            trashViewModel.clearSelectedNote()
            restoreDialogState.dismiss()
        },
        onAccept = {
            val note = trashViewModel.selectedNote.value
            trashViewModel.clearSelectedNote()
            actionViewModel.restoreNoteFromTrash(note)
            restoreDialogState.dismiss()
        },
    )

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrashTopAppBar(
                scrollBehavior = scrollBehavior,
                selection =
                    TrashTopAppBarSelection(
                        onNavigation = { homeState.openDrawer() },
                        selectedNotes = selectedNotes,
                        onCancelSelection = actionViewModel::cancelNoteSelection,
                        onRestoreSelected = actionViewModel::restoreSelectedNotesFromTrash,
                        onDeleteSelected = actionViewModel::deleteSelectedNotesFromTrash,
                    ),
                trashDropdownMenuSelection =
                    TrashDropdownMenuSelection(
                        onEmptyTrash = { emptyTrashDialogState.show() },
                    ),
            )
        },
        snackbarHost = { CustomSnackbarHost(state = homeState.snackbarHostState) },
        content = { paddingValues ->
            if (uiState.trashNotes.isEmpty()) {
                EmptyContentPlaceholder(
                    modifier =
                        Modifier
                            .padding(horizontal = 32.dp)
                            .padding(paddingValues)
                            .fillMaxSize(),
                    heroIcon = painterResource(id = AppIcons.Delete),
                    message = stringResource(id = AppStrings.Placeholder.NoNotesInTrash),
                )
            }

            NoteList(
                innerPadding = paddingValues,
                noteSelection =
                    NoteSelection(
                        noteStyle = visualState.noteStyle,
                        onClick = { note ->
                            if (selectedNotes.isEmpty()) {
                                trashViewModel.selectNote(note)
                                restoreDialogState.show()
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
                        NoteCategory(notes = uiState.trashNotes),
                    ),
                selectedNotes = selectedNotes,
                listStyle = visualState.listStyle,
                listHeader = {
                    TipCard(
                        isVisible = !uiState.trashTipCompleted,
                        message = stringResource(id = AppStrings.Tip.AutoDeleteTrash),
                        onClose = { trashViewModel.trashTipCompleted(true) },
                    )
                },
            )
        },
    )
}
