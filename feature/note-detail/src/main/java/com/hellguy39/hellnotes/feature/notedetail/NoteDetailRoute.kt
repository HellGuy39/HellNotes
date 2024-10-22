package com.hellguy39.hellnotes.feature.notedetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.lifecycle.collectAsEventsWithLifecycle
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.notedetail.components.NoteDetailChecklistSelection
import com.hellguy39.hellnotes.feature.notedetail.components.NoteDetailContentSelection
import com.hellguy39.hellnotes.feature.notedetail.components.NoteDetailTopAppBarSelection
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
        }
    }

    val noteDetailState = rememberNoteDetailState()

    val uiState by noteDetailViewModel.uiState.collectAsStateWithLifecycle()

    val shareDialogState = rememberDialogState()
    val confirmDialogState = rememberDialogState()

    val scope = rememberCoroutineScope()

    BackHandler {
        noteDetailViewModel.send(NoteDetailUiEvent.Close)
        navigateBack()
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

    var isOpenMenuBottomSheet by rememberSaveable { mutableStateOf(false) }
    val attachmentBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)

    var isOpenAttachmentBottomSheet by rememberSaveable { mutableStateOf(false) }
    val menuBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = false)

    fun closeMenuBottomSheet() {
        scope.launch {
            menuBottomSheetState.hide()
        }.invokeOnCompletion {
            if (!menuBottomSheetState.isVisible) {
                isOpenMenuBottomSheet = false
            }
        }
    }

    fun closeAttachmentBottomSheet() {
        scope.launch {
            attachmentBottomSheetState.hide()
        }.invokeOnCompletion {
            if (!attachmentBottomSheetState.isVisible) {
                isOpenAttachmentBottomSheet = false
            }
        }
    }

    val listItemModifier = Modifier.padding(16.dp)

    if (isOpenMenuBottomSheet) {
        val menuBottomSheetItems =
            remember {
                if (uiState.isReadOnly) {
                    mutableStateListOf(
                        BottomSheetMenuItemHolder(
                            title = UiText.StringResources(AppStrings.MenuItem.Restore),
                            icon = UiIcon.DrawableResources(AppIcons.RestoreFromTrash),
                            onClick = {
                                closeMenuBottomSheet()
                                noteDetailViewModel.send(NoteDetailUiEvent.Restore)
                            },
                        ),
                        BottomSheetMenuItemHolder(
                            title = UiText.StringResources(AppStrings.MenuItem.DeleteForever),
                            icon = UiIcon.DrawableResources(AppIcons.DeleteForever),
                            onClick = {
                                closeMenuBottomSheet()
                                noteDetailViewModel.send(NoteDetailUiEvent.DeleteForever)
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
                                confirmDialogState.show()
                            },
                        ),
                        BottomSheetMenuItemHolder(
                            title = UiText.StringResources(AppStrings.MenuItem.MakeACopy),
                            icon = UiIcon.DrawableResources(AppIcons.ContentCopy),
                            onClick = {
                                closeMenuBottomSheet()
                                noteDetailViewModel.send(NoteDetailUiEvent.CopyNote)
                                noteDetailState.showSnack(
                                    UiText.StringResources(AppStrings.Snack.NoteHasBeenCopied),
                                )
                            },
                        ),
                        BottomSheetMenuItemHolder(
                            title = UiText.StringResources(AppStrings.MenuItem.Share),
                            icon = UiIcon.DrawableResources(AppIcons.Share),
                            onClick = {
                                closeMenuBottomSheet()
                                val note = uiState.wrapper.note
                                if (note.isValid) {
                                    shareDialogState.show()
                                } else {
                                    noteDetailState.showSnack(
                                        UiText.StringResources(AppStrings.Snack.NothingToShare),
                                    )
                                }
                            },
                        ),
                    )
                }
            }

        ModalBottomSheet(
            modifier = Modifier,
            onDismissRequest = { isOpenMenuBottomSheet = false },
            sheetState = menuBottomSheetState,
            windowInsets = WindowInsets(0, 0, 0, 0),
        ) {
            Column(
                modifier = Modifier.padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
            ) {
                menuBottomSheetItems.forEach { item ->
                    HNListItem(
                        modifier = listItemModifier,
                        title = item.title,
                        iconSize = 24.dp,
                        heroIcon = item.icon,
                        onClick = item.onClick,
                    )
                }
            }
        }
    }

    if (isOpenAttachmentBottomSheet) {
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
                            noteDetailViewModel.send(NoteDetailUiEvent.AddChecklist)
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
            onDismissRequest = { isOpenAttachmentBottomSheet = false },
            sheetState = attachmentBottomSheetState,
            windowInsets = WindowInsets(0, 0, 0, 0),
        ) {
            Column(
                modifier = Modifier.padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()),
            ) {
                attachmentSheetItems.forEach { item ->
                    HNListItem(
                        modifier = listItemModifier,
                        title = item.title,
                        iconSize = 24.dp,
                        heroIcon = item.icon,
                        onClick = item.onClick,
                    )
                }
            }
        }
    }

    NoteDetailScreen(
        snackbarHost = { CustomSnackbarHost(state = noteDetailState.snackbarHostState) },
        uiState = uiState,
        noteDetailContentSelection =
            NoteDetailContentSelection(
                onTitleTextChanged = { newText ->
                    noteDetailViewModel.send(NoteDetailUiEvent.UpdateNoteTitle(newText))
                },
                onNoteTextChanged = { newText ->
                    noteDetailViewModel.send(NoteDetailUiEvent.UpdateNoteContent(newText))
                },
                onReminderClick = { reminder ->
                    if (uiState.isReadOnly) return@NoteDetailContentSelection
                    val noteId = uiState.wrapper.note.id ?: 0
                    val reminderId = reminder.id ?: 0
                    navigateToReminderEdit(noteId, reminderId)
                },
                onLabelClick = { label ->
                    if (uiState.isReadOnly) return@NoteDetailContentSelection
                    val id = uiState.wrapper.note.id ?: 0
                    navigateToLabelSelection(id)
                },
            ),
        topAppBarSelection =
            NoteDetailTopAppBarSelection(
                uiState = uiState,
                onNavigationButtonClick = {
                    noteDetailViewModel.send(NoteDetailUiEvent.Close)
                    navigateBack()
                },
                onPin = { isPinned ->
                    noteDetailViewModel.send(NoteDetailUiEvent.ToggleIsPinned)

                    noteDetailState.showSnack(
                        UiText.StringResources(AppStrings.Snack.notePinned(isPinned)),
                    )
                },
                onArchive = { isArchived ->
                    noteDetailViewModel.send(NoteDetailUiEvent.ToggleIsArchived)

                    noteDetailState.showSnack(
                        UiText.StringResources(AppStrings.Snack.noteArchived(isArchived)),
                    )
                },
                onReminder = {
                    val noteId = uiState.wrapper.note.id ?: 0
                    val reminderId = Arguments.ReminderId.emptyValue
                    navigateToReminderEdit(noteId, reminderId)
                },
            ),
        bottomBarSelection =
            NoteDetailBottomBarSelection(
                onMenu = {
                    isOpenMenuBottomSheet = !isOpenAttachmentBottomSheet
                },
                onAttachment = {
                    isOpenAttachmentBottomSheet = !isOpenAttachmentBottomSheet
                },
            ),
        noteDetailChecklistSelection =
            NoteDetailChecklistSelection(
                onCheckedChange = { checklist, item, isChecked ->
                    noteDetailViewModel.send(
                        NoteDetailUiEvent.UpdateChecklistItem(
                            checklist,
                            item,
                            item.copy(isChecked = isChecked),
                        ),
                    )
                },
                onDoneAll = { checklist ->
                    noteDetailViewModel.send(NoteDetailUiEvent.CheckAllChecklistItems(checklist, true))
                },
                onRemoveDone = { checklist ->
                    noteDetailViewModel.send(NoteDetailUiEvent.CheckAllChecklistItems(checklist, false))
                },
                onAddChecklistItem = { checklist ->
                    noteDetailViewModel.send(NoteDetailUiEvent.AddChecklistItem(checklist))
                },
                onDelete = { checklist ->
                    noteDetailViewModel.send(NoteDetailUiEvent.DeleteChecklist(checklist))
                },
                onDeleteChecklistItem = { checklist, item ->
                    noteDetailViewModel.send(NoteDetailUiEvent.DeleteChecklistItem(checklist, item))
                },
                onChecklistNameChange = { checklist, name ->
                    noteDetailViewModel.send(NoteDetailUiEvent.UpdateChecklistName(checklist, name))
                },
                onUpdateChecklistItemText = { checklist, item, text ->
                    noteDetailViewModel.send(
                        NoteDetailUiEvent.UpdateChecklistItem(
                            checklist,
                            item,
                            item.copy(text = text),
                        ),
                    )
                },
                onUpdateIsChecklistExpanded = { checklist, isExpanded ->
                    noteDetailViewModel.send(NoteDetailUiEvent.ExpandChecklist(checklist, isExpanded))
                },
            ),
    )
}
