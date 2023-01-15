package com.hellguy39.hellnotes.note_detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.common.date.DateHelper
import com.hellguy39.hellnotes.components.rememberDialogState
import com.hellguy39.hellnotes.domain.android_system_features.AlarmScheduler
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.components.*
import com.hellguy39.hellnotes.note_detail.util.ShareHelper
import com.hellguy39.hellnotes.note_detail.util.ShareType
import com.hellguy39.hellnotes.resources.HellNotesStrings
import com.hellguy39.hellnotes.system.BackHandler
import kotlinx.coroutines.launch


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    alarmScheduler: AlarmScheduler = noteDetailViewModel.alarmScheduler,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val note by noteDetailViewModel.note.collectAsStateWithLifecycle()
    val noteReminds by noteDetailViewModel.noteReminders.collectAsStateWithLifecycle()
    val allLabels by noteDetailViewModel.allLabels.collectAsStateWithLifecycle()
    val noteLabels by noteDetailViewModel.noteLabels.collectAsStateWithLifecycle()

    val reminderDialogState = rememberDialogState()
    val labelDialogState = rememberDialogState()
    val shareDialogState = rememberDialogState()

    val snackbarHostState = remember { SnackbarHostState() }

    LabelDialog(
        state = labelDialogState,
        selection = LabelDialogSelection(
            note = note,
            allLabels = allLabels,
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
                noteDetailViewModel.deleteLabel(label)
            },
            updateQuery = { query ->
                noteDetailViewModel.updateLabelQuery(query)
            }
        )
    )

    ShareDialog(
        state = shareDialogState,
        selection = ShareDialogSelection(
            shareAsPlainText = {
                ShareHelper(context).share(note, ShareType.PlainText)
            },
            shareAsTxtFile = {
                ShareHelper(context).share(note, ShareType.TxtFile)
            }
        )
    )

    var editableRemind: Remind? by remember { mutableStateOf(null) }
    val remindIsTooLateMessage = stringResource(id = HellNotesStrings.Text.RemindTimeIsTooLate)

    ReminderDialog(
        state = reminderDialogState,
        selection = ReminderDialogSelection(
            note = note,
            editableRemind = editableRemind,
            onCreateRemind = { remind ->
                if(remind.triggerDate > DateHelper(context).getCurrentTimeInEpochMilli()) {
                    noteDetailViewModel.insertRemind(remind)
                    alarmScheduler.scheduleAlarm(remind)
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = remindIsTooLateMessage)
                    }
                }
            },
            onDeleteRemind = { remind ->
                val existedAlarm = noteReminds.find { it.id == remind.id }
                existedAlarm?.let {
                    alarmScheduler.cancelAlarm(it)
                    noteDetailViewModel.deleteRemind(existedAlarm)
                }
            },
            onUpdateRemind = { remind ->
                val oldRemind = noteReminds.find { it.id == remind.id }
                oldRemind?.let { alarmScheduler.cancelAlarm(it) }
                alarmScheduler.scheduleAlarm(remind)
                noteDetailViewModel.updateRemind(remind)
            }
        )
    )

    BackHandler(
        onBack = {
            noteDetailViewModel.discardNoteIfEmpty()
            navController.popBackStack()
        }
    )

    NoteDetailScreen(
        snackbarHostState = snackbarHostState,
        noteDetailContentSelection = NoteDetailContentSelection(
            note = note,
            noteReminds = noteReminds,
            noteLabels = noteLabels,
            onTitleTextChanged = { newText -> noteDetailViewModel.updateNoteTitle(newText) },
            onNoteTextChanged = { newText -> noteDetailViewModel.updateNoteContent(newText) },
            onEditRemind = { remind ->
                editableRemind = remind
                reminderDialogState.show()
            }
        ),
        noteDetailDropdownMenuSelection = NoteDetailDropdownMenuSelection(
            onColor = {},
            onLabels = {
                labelDialogState.show()
            },
            onShare = {
                shareDialogState.show()
            },
            onDelete = {
                noteDetailViewModel.deleteNote()
                navController.popBackStack()
            },
        ),
        noteDetailTopAppBarSelection = NoteDetailTopAppBarSelection(
            note = note,
            onNavigationButtonClick = {
                noteDetailViewModel.discardNoteIfEmpty()
                navController.popBackStack()
            },
            onReminder = {
                editableRemind = null
                reminderDialogState.show()
            },
            onPin = { isPinned ->
                noteDetailViewModel.updateIsPinned(isPinned)
            },
            onColorSelected = { colorHex ->
                noteDetailViewModel.updateNoteBackground(colorHex)
            }
        )
    )
}