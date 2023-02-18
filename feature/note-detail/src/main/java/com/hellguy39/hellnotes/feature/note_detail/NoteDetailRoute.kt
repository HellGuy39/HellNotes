package com.hellguy39.hellnotes.feature.note_detail

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.isNoteValid
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReminderEdit
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.note_detail.components.*
import com.hellguy39.hellnotes.feature.note_detail.util.ShareHelper
import com.hellguy39.hellnotes.feature.note_detail.util.ShareType
import kotlinx.coroutines.launch

@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    dateHelper: DateHelper = noteDetailViewModel.dateHelper
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val uiState by noteDetailViewModel.uiState.collectAsStateWithLifecycle()

    val reminderDialogState = rememberDialogState()
    val labelDialogState = rememberDialogState()
    val shareDialogState = rememberDialogState()

    val snackbarHostState = remember { SnackbarHostState() }

    LabelDialog(
        state = labelDialogState,
        selection = LabelDialogSelection(
            note = uiState.note,
            allLabels = uiState.searchedLabels,
            selectLabel = { label ->
                noteDetailViewModel.selectLabel(label)
            },
            unselectLabel = { label ->
                noteDetailViewModel.unselectLabel(label)
            },
            createLabel = { name ->
                noteDetailViewModel.insertLabel(Label(name = name))
            },
            deleteLabel = { label ->
                noteDetailViewModel.onDeleteLabel(label)
            },
            updateQuery = { query ->
                noteDetailViewModel.onUpdateLabelSearch(query)
            }
        )
    )

    ShareDialog(
        state = shareDialogState,
        selection = ShareDialogSelection(
            shareAsPlainText = {
                ShareHelper(context).share(
                    uiState.note,
                    ShareType.PlainText
                )
            },
            shareAsTxtFile = {
                ShareHelper(context).share(
                    uiState.note,
                    ShareType.TxtFile
                )
            }
        )
    )

    BackHandler(
        onBack = {
            navController.popBackStack()
        }
    )

    val lifecycleOwner = LocalLifecycleOwner.current

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

    val nothingToShare = stringResource(id = HellNotesStrings.Text.NothingToShare)
    val snackNotePinned = stringResource(id = HellNotesStrings.Snack.NotePinned)
    val snackNoteUnpinned = stringResource(id = HellNotesStrings.Snack.NoteUnpinned)
    val snackNoteArchived = stringResource(id = HellNotesStrings.Snack.NoteArchived)
    val snackNoteUnarchived = stringResource(id = HellNotesStrings.Snack.NoteUnarchived)

    NoteDetailScreen(
        snackbarHostState = snackbarHostState,
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
                labelDialogState.show()
            }
        ),
        noteDetailDropdownMenuSelection = NoteDetailDropdownMenuSelection(
            onColor = {  },
            onLabels = {
                labelDialogState.show()
            },
            onShare = {
                if (uiState.note.isNoteValid()) {
                    shareDialogState.show()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = nothingToShare)
                    }
                }
            },
            onDelete = {
                noteDetailViewModel.onDeleteNote()
                navController.popBackStack()
            },
        ),
        noteDetailTopAppBarSelection = NoteDetailTopAppBarSelection(
            note = uiState.note,
            onNavigationButtonClick = {
                navController.popBackStack()
            },
            onReminder = {
                navController.navigateToReminderEdit(
                    noteId = uiState.note.id,
                    reminderId = ArgumentDefaultValues.NewReminder
                )
            },
            onPin = { isPinned ->
                noteDetailViewModel.onUpdateIsPinned(isPinned)
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (isPinned) snackNotePinned else snackNoteUnpinned,
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            },
            onColorSelected = { colorHex ->
                //noteDetailViewModel.onUpdateNoteBackground(colorHex)
            },
            onArchive = { isArchived ->
                noteDetailViewModel.onUpdateIsArchived(isArchived)
                snackbarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (isArchived) snackNoteArchived else snackNoteUnarchived,
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        ),
        dateHelper = dateHelper
    )
}