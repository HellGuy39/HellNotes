package com.hellguy39.hellnotes.feature.note_detail

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.isNoteValid
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.items.SelectionItem
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.components.snack.SnackAction
import com.hellguy39.hellnotes.core.ui.components.snack.getSnackMessage
import com.hellguy39.hellnotes.core.ui.components.snack.showDismissableSnackbar
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReminderEdit
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.note_detail.components.NoteDetailChecklistSelection
import com.hellguy39.hellnotes.feature.note_detail.components.NoteDetailContentSelection
import com.hellguy39.hellnotes.feature.note_detail.components.NoteDetailDropdownMenuSelection
import com.hellguy39.hellnotes.feature.note_detail.components.NoteDetailTopAppBarSelection
import com.hellguy39.hellnotes.feature.note_detail.util.ShareUtils
import com.hellguy39.hellnotes.feature.note_detail.util.ShareType

@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val uiState by noteDetailViewModel.uiState.collectAsStateWithLifecycle()

    val shareDialogState = rememberDialogState()
    val confirmDialogState = rememberDialogState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val onBackNavigation: () -> Unit = {
        navController.popBackStack()
    }

    fun onShare(type: ShareType)  {
        ShareUtils.share(
            context = context,
            note = uiState.note,
            type = type
        )
    }

    BackHandler(onBack = onBackNavigation)

    CustomDialog(
        state = shareDialogState,
        heroIcon = painterResource(id = HellNotesIcons.Share),
        title = stringResource(id = HellNotesStrings.Title.Share),
        message = stringResource(id = HellNotesStrings.Helper.ShareDialog),
        onCancel = { shareDialogState.dismiss() },
        content = {
            Spacer(modifier = Modifier.height(8.dp))
            SelectionItem(
                title = stringResource(id = HellNotesStrings.MenuItem.TxtFile),
                onClick = {
                    shareDialogState.dismiss()
                    onShare(ShareType.TxtFile)
                },
            )
            SelectionItem(
                title = stringResource(id = HellNotesStrings.MenuItem.PlainText),
                onClick = {
                    shareDialogState.dismiss()
                    onShare(ShareType.PlainText)
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    )

    CustomDialog(
        state = confirmDialogState,
        heroIcon = painterResource(id = HellNotesIcons.Delete),
        title = stringResource(id = HellNotesStrings.Title.DeleteThisNote),
        message = stringResource(id = HellNotesStrings.Helper.DeleteNoteDialog),
        onCancel = { confirmDialogState.dismiss() },
        onAccept = {
            confirmDialogState.dismiss()
            noteDetailViewModel.onDeleteNote()
            navController.popBackStack()
        }
    )

    val currentOnStop by rememberUpdatedState {
        noteDetailViewModel.onDiscardNoteIfEmpty()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_STOP -> currentOnStop()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    NoteDetailScreen(
        snackbarHost = { CustomSnackbarHost(state = snackbarHostState) },
        uiState = uiState,
        noteDetailContentSelection = NoteDetailContentSelection(
            onTitleTextChanged = { newText -> noteDetailViewModel.onUpdateNoteTitle(newText) },
            onNoteTextChanged = { newText -> noteDetailViewModel.onUpdateNoteContent(newText) },
            onReminderClick = { reminder ->
                navController.navigateToReminderEdit(
                    noteId = uiState.note.id,
                    reminderId = reminder.id
                )
            },
            onLabelClick = { label ->
                navController.navigateToLabelSelection(uiState.note.id)
            }
        ),
        dropdownMenuSelection = NoteDetailDropdownMenuSelection(
            onShare = {
                if (uiState.note.isNoteValid()) {
                    shareDialogState.show()
                } else {
                    snackbarHostState.showDismissableSnackbar(
                        scope = scope,
                        message = context.getString(HellNotesStrings.Text.NothingToShare),
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onDelete = {
                confirmDialogState.show()
            },
        ),
        topAppBarSelection = NoteDetailTopAppBarSelection(
            note = uiState.note,
            onNavigationButtonClick = onBackNavigation,
            onPin = { isPinned ->
                noteDetailViewModel.onUpdateIsPinned(isPinned)

                val snackAction = if (isPinned) SnackAction.Pinned else SnackAction.Unpinned
                snackbarHostState.showDismissableSnackbar(
                    scope = scope,
                    message = snackAction.getSnackMessage(context),
                    duration = SnackbarDuration.Short
                )
            },
            onArchive = { isArchived ->
                noteDetailViewModel.onUpdateIsArchived(isArchived)
                val snackAction = if (isArchived) SnackAction.Archive else SnackAction.Unarchive
                snackbarHostState.showDismissableSnackbar(
                    scope = scope,
                    message = snackAction.getSnackMessage(context = context, isSingleItem = true),
                    duration = SnackbarDuration.Short
                )
            }
        ),
        bottomBarSelection = NoteDetailBottomBarSelection(
            onReminder = {
                navController.navigateToReminderEdit(
                    noteId = uiState.note.id,
                    reminderId = ArgumentDefaultValues.NewReminder
                )
            },
            onLabels = {
                navController.navigateToLabelSelection(uiState.note.id)
            },
            onChecklist = {
                noteDetailViewModel.onAddChecklistItem()
            }
        ),
        noteDetailChecklistSelection = NoteDetailChecklistSelection(
            onRemoveItem = { item ->
                noteDetailViewModel.onRemoveChecklistItem(item)
            },
            onChangeText = { item, text ->
                noteDetailViewModel.onUpdateChecklistItemText(item, text)
            },
            onCheckedChange = { item, isChecked ->
                noteDetailViewModel.onUpdateChecklistItemChecked(item, isChecked)
            },
            onNewItem = {
                noteDetailViewModel.onAddChecklistItem()
            },
            onMoveItem = { fromIndex, toIndex ->
                noteDetailViewModel.onMoveChecklistItem(toIndex = toIndex, fromIndex = fromIndex)
            }
        )
    )
}