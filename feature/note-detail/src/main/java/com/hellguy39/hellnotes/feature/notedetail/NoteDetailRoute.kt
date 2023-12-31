package com.hellguy39.hellnotes.feature.notedetail

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.repository.local.database.isNoteValid
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.components.snack.SnackAction
import com.hellguy39.hellnotes.core.ui.components.snack.getSnackMessage
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.notedetail.components.NoteDetailChecklistSelection
import com.hellguy39.hellnotes.feature.notedetail.components.NoteDetailContentSelection
import com.hellguy39.hellnotes.feature.notedetail.components.NoteDetailTopAppBarSelection
import com.hellguy39.hellnotes.feature.notedetail.util.BottomSheetMenuItemHolder
import com.hellguy39.hellnotes.feature.notedetail.util.ShareType
import com.hellguy39.hellnotes.feature.notedetail.util.ShareUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailRoute(
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLabelSelection: (id: Long) -> Unit,
    navigateToReminderEdit: (noteId: Long, reminderId: Long) -> Unit,
) {
    val context = LocalContext.current

    val uiState by noteDetailViewModel.uiState.collectAsStateWithLifecycle()

    val shareDialogState = rememberDialogState()
    val confirmDialogState = rememberDialogState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun onShare(type: ShareType) {
        uiState.let { state ->
            ShareUtils.share(
                context = context,
                note = state.wrapper.note,
                type = type,
            )
        }
    }

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
                    onShare(ShareType.TxtFile)
                },
            )
            HNListItem(
                modifier = listItemModifier,
                title = stringResource(id = AppStrings.MenuItem.PlainText),
                onClick = {
                    shareDialogState.dismiss()
                    onShare(ShareType.PlainText)
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
            noteDetailViewModel.send(NoteDetailUiEvent.DeleteNote)
            navigateBack()
        },
    )

    var isOpenMenuBottomSheet by rememberSaveable { mutableStateOf(false) }
    val attachmentBottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )

    var isOpenAttachmentBottomSheet by rememberSaveable { mutableStateOf(false) }
    val menuBottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )

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

    val toast = Toast.makeText(context, context.getString(AppStrings.Toast.ComingSoon), Toast.LENGTH_SHORT)

    val menuBottomSheetItems =
        listOf(
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Delete),
                icon = painterResource(id = AppIcons.Delete),
                onClick = {
                    closeMenuBottomSheet()
                    confirmDialogState.show()
                },
            ),
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.MakeACopy),
                icon = painterResource(id = AppIcons.ContentCopy),
                onClick = {
                    closeMenuBottomSheet()
                    noteDetailViewModel.send(NoteDetailUiEvent.CopyNote)
                },
            ),
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Share),
                icon = painterResource(id = AppIcons.Share),
                onClick = {
                    closeMenuBottomSheet()
                    val note = uiState.wrapper.note
                    if (note.isNoteValid()) {
                        shareDialogState.show()
                    } else {
                        snackbarHostState.showDismissableSnackbar(
                            scope = scope,
                            message = context.getString(AppStrings.Snack.NothingToShare),
                            duration = SnackbarDuration.Short,
                        )
                    }
                },
            ),
        )

    val attachmentSheetItems =
        listOf(
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.TakeAPhoto),
                icon = painterResource(id = AppIcons.PhotoCamera),
                onClick = {
                    closeAttachmentBottomSheet()
                    toast.show()
                },
            ),
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Image),
                icon = painterResource(id = AppIcons.Image),
                onClick = {
                    closeAttachmentBottomSheet()
                    toast.show()
                },
            ),
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Recording),
                icon = painterResource(id = AppIcons.Mic),
                onClick = {
                    closeAttachmentBottomSheet()
                    toast.show()
                },
            ),
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Place),
                icon = painterResource(id = AppIcons.PinDrop),
                onClick = {
                    closeAttachmentBottomSheet()
                    toast.show()
                },
            ),
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Checklist),
                icon = painterResource(id = AppIcons.Checklist),
                onClick = {
                    closeAttachmentBottomSheet()
                    noteDetailViewModel.send(NoteDetailUiEvent.AddChecklist)
                },
            ),
            BottomSheetMenuItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Labels),
                icon = painterResource(id = AppIcons.Label),
                onClick = {
                    closeAttachmentBottomSheet()
                    val id = uiState.wrapper.note.id ?: 0
                    navigateToLabelSelection(id)
                },
            ),
        )

    if (isOpenMenuBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier,
            onDismissRequest = { isOpenMenuBottomSheet = false },
            sheetState = menuBottomSheetState,
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

    if (isOpenAttachmentBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier,
            onDismissRequest = { isOpenAttachmentBottomSheet = false },
            sheetState = attachmentBottomSheetState,
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

    NoteDetailScreen(
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
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
                    val noteId = uiState.wrapper.note.id ?: 0
                    val reminderId = reminder.id ?: 0
                    navigateToReminderEdit(noteId, reminderId)
                },
                onLabelClick = { label ->
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

                    val snackAction = if (isPinned) SnackAction.Pinned else SnackAction.Unpinned

                    snackbarHostState.showDismissableSnackbar(
                        scope = scope,
                        message = snackAction.getSnackMessage(context),
                        duration = SnackbarDuration.Short,
                    )
                },
                onArchive = { isArchived ->
                    noteDetailViewModel.send(NoteDetailUiEvent.ToggleIsArchived)

                    val snackAction = if (isArchived) SnackAction.Archive else SnackAction.Unarchive

                    snackbarHostState.showDismissableSnackbar(
                        scope = scope,
                        message = snackAction.getSnackMessage(context = context, isSingleItem = true),
                        duration = SnackbarDuration.Short,
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
