/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.home.HomeState
import com.hellguy39.hellnotes.feature.home.VisualsViewModel

@Composable
fun TrashRoute(
    homeState: HomeState,
    navigateToNoteDetail: (id: Long?) -> Unit,
    trashViewModel: TrashViewModel = hiltViewModel(),
    visualsViewModel: VisualsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "TrashScreen")

    trashViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            is TrashNavigationEvent.NavigateToNoteDetail -> {
                navigateToNoteDetail(event.noteId)
            }
        }
    }

    // val restoreDialogState = rememberDialogState()
    val emptyTrashDialogState = rememberDialogState()

    val uiState by trashViewModel.uiState.collectAsStateWithLifecycle()
    val visualState by visualsViewModel.visualState.collectAsStateWithLifecycle()

    CustomDialog(
        state = emptyTrashDialogState,
        heroIcon = painterResource(id = AppIcons.DeleteSweep),
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

//    CustomDialog(
//        state = restoreDialogState,
//        heroIcon = painterResource(id = AppIcons.RestoreFromTrash),
//        title = stringResource(id = AppStrings.Title.RestoreThisNote),
//        message = stringResource(id = AppStrings.Supporting.RestoreNote),
//        onClose = {
//            restoreDialogState.dismiss()
//        },
//        onCancel = {
//            restoreDialogState.dismiss()
//        },
//        onAccept = {
//            //val note = trashViewModel.selectedNote.value
//            // actionViewModel.restoreNoteFromTrash(note)
//            restoreDialogState.dismiss()
//        },
//    )

    TrashScreen(
        uiState = uiState,
        visualState = visualState,
        onNoteClick = remember { { noteId -> trashViewModel.onNoteClick(noteId) } },
        onNotePress = remember { { noteId -> trashViewModel.onNotePress(noteId) } },
        onNavigationClick = remember { { homeState.openDrawer() } },
        onCancelSelectionClick = remember { { trashViewModel.onCancelItemSelection() } },
        onRestoreSelectedClick = remember { { trashViewModel.onRestoreSelectedItems() } },
        onDeleteForeverSelectedClick = remember { { trashViewModel.onDeleteForeverSelectedItems() } },
        onEmptyTrashClick = remember { { emptyTrashDialogState.show() } },
        onCloseTrashTip = remember { { trashViewModel.trashTipCompleted(true) } },
        snackbarHostState = homeState.snackbarHostState,
    )
}
