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
package com.hellguy39.hellnotes.feature.notedetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarController
import com.hellguy39.hellnotes.core.ui.components.snack.SnackbarEvent
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.IconSize
import com.hellguy39.hellnotes.feature.notedetail.util.BottomSheetMenuItemHolder
import com.hellguy39.hellnotes.feature.notedetail.util.ShareType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailRoute(
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLabelSelection: (id: Long) -> Unit,
    navigateToReminderEdit: (noteId: Long, reminderId: Long) -> Unit,
) {
    TrackScreenView(screenName = "NoteDetailScreen")

    noteDetailViewModel.navigationEvents.collectAsEventsWithLifecycle { event ->
        when (event) {
            NoteDetailNavigationEvent.NavigateBack -> {
                navigateBack()
            }

            is NoteDetailNavigationEvent.NavigateLabelSelection -> {
                navigateToLabelSelection(event.id)
            }

            is NoteDetailNavigationEvent.NavigateToReminderEdit -> {
                navigateToReminderEdit(event.noteId, event.reminderId)
            }
        }
    }

    val noteDetailState = rememberNoteDetailState()

    val uiState by noteDetailViewModel.uiState.collectAsStateWithLifecycle()

    val shareDialogState = rememberDialogState()
    val confirmDialogState = rememberDialogState()

    val scope = rememberCoroutineScope()

    BackHandler {
        noteDetailViewModel.send(NoteDetailUiEvent.Close)
    }

    CustomDialog(
        state = shareDialogState,
        heroIcon = painterResource(id = AppIcons.Share),
        title = stringResource(id = AppStrings.Title.Share),
        message = stringResource(id = AppStrings.Supporting.ShareNote),
        onCancel = { shareDialogState.dismiss() },
        content = {
            val listItemModifier = Modifier.padding(16.dp)
            Spacer(modifier = Modifier.height(8.dp))
            HNListItem(
                modifier = listItemModifier,
                title = stringResource(id = AppStrings.MenuItem.TxtFile),
                onClick = {
                    shareDialogState.dismiss()
                    noteDetailState.shareNote(ShareType.TxtFile, uiState.wrapper)
                },
            )
            HNListItem(
                modifier = listItemModifier,
                title = stringResource(id = AppStrings.MenuItem.PlainText),
                onClick = {
                    shareDialogState.dismiss()
                    noteDetailState.shareNote(ShareType.PlainText, uiState.wrapper)
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
        },
    )

    CustomDialog(
        state = confirmDialogState,
        heroIcon = painterResource(id = AppIcons.Delete),
        title = stringResource(id = AppStrings.Title.DeleteThisNote),
        message = stringResource(id = AppStrings.Supporting.DeleteNote),
        onCancel = { confirmDialogState.dismiss() },
        onAccept = {
            confirmDialogState.dismiss()
            noteDetailViewModel.send(NoteDetailUiEvent.MoveToTrash)
            navigateBack()
        },
    )

    val attachmentBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val menuBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)

    fun closeMenuBottomSheet() {
        scope.launch {
            menuBottomSheetState.hide()
        }.invokeOnCompletion {
            if (!menuBottomSheetState.isVisible) {
                noteDetailViewModel.send(NoteDetailUiEvent.ShowMenu(false))
            }
        }
    }

    fun closeAttachmentBottomSheet() {
        scope.launch {
            attachmentBottomSheetState.hide()
        }.invokeOnCompletion {
            if (!attachmentBottomSheetState.isVisible) {
                noteDetailViewModel.send(NoteDetailUiEvent.ShowAttachment(false))
            }
        }
    }

    MenuModalBottomSheetDialog(
        menuBottomSheetState = menuBottomSheetState,
        uiState = uiState,
        onUiEvent = { noteDetailViewModel.send(it) },
        showConfirmDialog = { confirmDialogState.show() } ,
        showShareDialog = { shareDialogState.show() },
        closeMenuBottomSheet = { closeMenuBottomSheet() },
    )

    AttachmentModalBottomSheetDialog(
        attachmentBottomSheetState = attachmentBottomSheetState,
        uiState = uiState,
        noteDetailState = noteDetailState,
        navigateToLabelSelection = navigateToLabelSelection,
        onUiEvent = { noteDetailViewModel.send(it) },
        closeAttachmentBottomSheet = { closeAttachmentBottomSheet() },
    )

    NoteDetailScreen(
        uiState = uiState,
        onUiEvent = { event -> noteDetailViewModel.send(event) },
        onNavigationButtonClick = { noteDetailViewModel.send(NoteDetailUiEvent.Close) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuModalBottomSheetDialog(
    menuBottomSheetState: SheetState,
    uiState: NoteDetailUiState,
    onUiEvent: (event: NoteDetailUiEvent) -> Unit,
    showConfirmDialog: () -> Unit,
    showShareDialog: () -> Unit,
    closeMenuBottomSheet: () -> Unit
) {
    if (!uiState.isMenuOpen) return

    val scope = rememberCoroutineScope()

    val menuBottomSheetItems =
        remember {
            if (uiState.isReadOnly) {
                mutableStateListOf(
                    BottomSheetMenuItemHolder(
                        title = UiText.StringResources(AppStrings.MenuItem.Restore),
                        icon = UiIcon.DrawableResources(AppIcons.RestoreFromTrash),
                        onClick = {
                            closeMenuBottomSheet()
                            onUiEvent(NoteDetailUiEvent.Restore)
                        },
                    ),
                    BottomSheetMenuItemHolder(
                        title = UiText.StringResources(AppStrings.MenuItem.DeleteForever),
                        icon = UiIcon.DrawableResources(AppIcons.DeleteForever),
                        onClick = {
                            closeMenuBottomSheet()
                            onUiEvent(NoteDetailUiEvent.DeleteForever)
                        },
                    ),
                )
            } else {
                mutableStateListOf(
                    BottomSheetMenuItemHolder(
                        title = UiText.StringResources(AppStrings.MenuItem.Delete),
                        icon = UiIcon.DrawableResources(AppIcons.Delete),
                        onClick = {
                            closeMenuBottomSheet()
                            showConfirmDialog()
                        },
                    ),
                    BottomSheetMenuItemHolder(
                        title = UiText.StringResources(AppStrings.MenuItem.MakeACopy),
                        icon = UiIcon.DrawableResources(AppIcons.ContentCopy),
                        onClick = {
                            closeMenuBottomSheet()
                            onUiEvent(NoteDetailUiEvent.CopyNote)
                            scope.launch {
                                SnackbarController.sendEvent(
                                    SnackbarEvent(
                                        text = UiText.StringResources(AppStrings.Snack.NoteHasBeenCopied)
                                    )
                                )
                            }
                        },
                    ),
                    BottomSheetMenuItemHolder(
                        title = UiText.StringResources(AppStrings.MenuItem.Share),
                        icon = UiIcon.DrawableResources(AppIcons.Share),
                        onClick = {
                            closeMenuBottomSheet()
                            val note = uiState.wrapper.note
                            if (note.isValid) {
                                showShareDialog()
                            } else {
                                scope.launch {
                                    SnackbarController.sendEvent(
                                        SnackbarEvent(
                                            text = UiText.StringResources(AppStrings.Snack.NothingToShare)
                                        )
                                    )
                                }
                            }
                        },
                    ),
                )
            }
        }

    ModalBottomSheet(
        modifier = Modifier,
        onDismissRequest = { onUiEvent(NoteDetailUiEvent.ShowMenu(false))  },
        sheetState = menuBottomSheetState,
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = WindowInsets
                        .navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                ),
        ) {
            menuBottomSheetItems.forEach { item ->
                HNListItem(
                    headlineContent = { Text(item.title.asString()) },
                    leadingContent = {
                        Icon(
                            modifier = Modifier.size(IconSize.medium),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            painter = item.icon.asPainter(),
                            contentDescription = null
                        )
                    },
                    onClick = item.onClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentModalBottomSheetDialog(
    attachmentBottomSheetState: SheetState,
    uiState: NoteDetailUiState,
    noteDetailState: HomeState,
    navigateToLabelSelection: (id: Long) -> Unit,
    onUiEvent: (event: NoteDetailUiEvent) -> Unit,
    closeAttachmentBottomSheet: () -> Unit
) {
    if (!uiState.isAttachmentOpen) return

    val attachmentSheetItems =
        remember {
            mutableStateListOf(
                BottomSheetMenuItemHolder(
                    title = UiText.StringResources(AppStrings.MenuItem.TakeAPhoto),
                    icon = UiIcon.DrawableResources(AppIcons.PhotoCamera),
                    onClick = {
                        closeAttachmentBottomSheet()
                        noteDetailState.showToast(UiText.StringResources(AppStrings.Toast.ComingSoon))
                    },
                ),
                BottomSheetMenuItemHolder(
                    title = UiText.StringResources(AppStrings.MenuItem.Image),
                    icon = UiIcon.DrawableResources(AppIcons.Image),
                    onClick = {
                        closeAttachmentBottomSheet()
                        noteDetailState.showToast(UiText.StringResources(AppStrings.Toast.ComingSoon))
                    },
                ),
                BottomSheetMenuItemHolder(
                    title = UiText.StringResources(AppStrings.MenuItem.Recording),
                    icon = UiIcon.DrawableResources(AppIcons.Mic),
                    onClick = {
                        closeAttachmentBottomSheet()
                        noteDetailState.showToast(UiText.StringResources(AppStrings.Toast.ComingSoon))
                    },
                ),
                BottomSheetMenuItemHolder(
                    title = UiText.StringResources(AppStrings.MenuItem.Place),
                    icon = UiIcon.DrawableResources(AppIcons.PinDrop),
                    onClick = {
                        closeAttachmentBottomSheet()
                        noteDetailState.showToast(UiText.StringResources(AppStrings.Toast.ComingSoon))
                    },
                ),
                BottomSheetMenuItemHolder(
                    title = UiText.StringResources(AppStrings.MenuItem.Checklist),
                    icon = UiIcon.DrawableResources(AppIcons.Checklist),
                    onClick = {
                        closeAttachmentBottomSheet()
                        onUiEvent(NoteDetailUiEvent.AddChecklist)
                    },
                ),
                BottomSheetMenuItemHolder(
                    title = UiText.StringResources(AppStrings.MenuItem.Labels),
                    icon = UiIcon.DrawableResources(AppIcons.Label),
                    onClick = {
                        closeAttachmentBottomSheet()
                        val id = uiState.wrapper.note.id ?: 0
                        navigateToLabelSelection(id)
                    },
                ),
            )
        }

    ModalBottomSheet(
        modifier = Modifier,
        onDismissRequest = { onUiEvent(NoteDetailUiEvent.ShowAttachment(false)) },
        sheetState = attachmentBottomSheetState,
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = WindowInsets
                        .navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                ),
        ) {
            attachmentSheetItems.forEach { item ->
                HNListItem(
                    headlineContent = { Text(item.title.asString()) },
                    leadingContent = {
                        Icon(
                            modifier = Modifier.size(IconSize.medium),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            painter = item.icon.asPainter(),
                            contentDescription = null
                        )
                    },
                    onClick = item.onClick
                )
            }
        }
    }
}
