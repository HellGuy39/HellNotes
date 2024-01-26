package com.hellguy39.hellnotes.feature.home.trash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@Composable
fun TrashRoute(
    homeState: HomeState,
    trashViewModel: TrashViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "TrashScreen")

    val restoreDialogState = rememberDialogState()
    val emptyTrashDialogState = rememberDialogState()

    val uiState by trashViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

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
            // actionViewModel.restoreNoteFromTrash(note)
            restoreDialogState.dismiss()
        },
    )

    TrashScreen(
        uiState = uiState,
        visualState = visualState,
        onNoteClick = remember { { index -> } },
        onNotePress = remember { { index -> } },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onCancelSelectionClick = remember { { } },
        onRestoreSelectedClick = remember { { } },
        onDeleteSelectedClick = remember { { } },
        onEmptyTrashClick = remember { { emptyTrashDialogState.show() } },
        onCloseTrashTip = remember { { trashViewModel.trashTipCompleted(true) } },
        snackbarHostState = homeState.snackbarHostState,
    )
}
