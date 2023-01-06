package com.hellguy39.hellnotes.note_detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.common.date.DateHelper
import com.hellguy39.hellnotes.domain.AlarmEvents
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.events.*
import com.hellguy39.hellnotes.note_detail.util.ShareHelper
import com.hellguy39.hellnotes.note_detail.util.ShareType
import com.hellguy39.hellnotes.resources.HellNotesStrings
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    alarmEvents: AlarmEvents = noteDetailViewModel.alarmEvents
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val note by noteDetailViewModel.note.collectAsState()
    val noteReminds by noteDetailViewModel.noteReminds.collectAsState()
    val allLabels by noteDetailViewModel.allLabels.collectAsState()
    val noteLabels by noteDetailViewModel.noteLabels.collectAsState()

    var editableRemind: Remind? by remember { mutableStateOf(null) }

    var isShowMenu by remember { mutableStateOf(false) }
    var isShowRemindDialog by remember { mutableStateOf(false) }
    var isShowEditRemindDialog by remember { mutableStateOf(false) }
    var isShowShareDialog by remember { mutableStateOf(false) }
    var isShowLabelDialog by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    val remindIsTooLateMessage = stringResource(id = HellNotesStrings.Text.RemindTimeIsTooLate)
    val reminderDialogEvents = object : ReminderDialogEvents {
        override fun show() { isShowRemindDialog = true }
        override fun dismiss() { isShowRemindDialog = false }
        override fun onCreateRemind(remind: Remind) {
            if(remind.triggerDate > DateHelper(context).getCurrentTimeInEpochMilli()) {
                noteDetailViewModel.insertRemind(remind)
                alarmEvents.scheduleAlarm(remind)
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar(message = remindIsTooLateMessage)
                }
            }
        }
    }

    val editReminderDialogEvents = object : EditReminderDialogEvents {
        override fun show() { isShowEditRemindDialog = true }
        override fun dismiss() { isShowEditRemindDialog = false }
        override fun setRemindToEdit(remind: Remind) { editableRemind = remind }
        override fun deleteRemind(remind: Remind) {
            val existedAlarm = noteReminds.find { it.id == remind.id }
            existedAlarm?.let {
                alarmEvents.cancelAlarm(it)
                noteDetailViewModel.deleteRemind(existedAlarm)
            }
        }
        override fun updateRemind(remind: Remind) {
            val oldRemind = noteReminds.find { it.id == remind.id }
            oldRemind?.let { alarmEvents.cancelAlarm(it) }
            alarmEvents.scheduleAlarm(remind)
            noteDetailViewModel.updateRemind(remind)
        }
    }

    val labelDialogEvents = object : LabelDialogEvents {
        override fun show() { isShowLabelDialog = true }
        override fun dismiss() { isShowLabelDialog = false }
        override fun selectLabel(label: Label) { noteDetailViewModel.selectLabel(label) }
        override fun unselectLabel(label: Label) { noteDetailViewModel.unselectLabel(label) }
        override fun createLabel(name: String) { noteDetailViewModel.insertLabel(Label(name = name)) }
        override fun updateQuery(query: String) { noteDetailViewModel.updateLabelQuery(query) }
    }

    val shareDialogEvents = object : ShareDialogEvents {
        override fun show() { isShowShareDialog = true }
        override fun dismiss() { isShowShareDialog = false }
        override fun shareAsTxtFile(note: Note) {
            ShareHelper(context).share(note, ShareType.TxtFile)
        }
        override fun shareAsPlainText(note: Note) {
            ShareHelper(context).share(note, ShareType.PlainText)
        }
    }

    val menuEvents = object : MenuEvents {
        override fun onDismissMenu() { isShowMenu = false }
        override fun onColor() {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "This feature isn't available yet"
                )
            }
        }
        override fun onLabels() { labelDialogEvents.show() }
        override fun onShare() { shareDialogEvents.show() }
        override fun onDelete() { noteDetailViewModel.deleteNote(); navController.popBackStack() }
    }

    val topAppBarEvents = object : TopAppBarEvents {
        override fun onReminder() { reminderDialogEvents.show() }
        override fun onPin(isPinned: Boolean) { noteDetailViewModel.updateIsPinned(isPinned) }
        override fun onColorSelected(colorHex: Long) { noteDetailViewModel.updateNoteBackground(colorHex) }
        override fun onMoreMenu() { isShowMenu = true }
    }

    NoteDetailScreen(
        onNavigationButtonClick = {
            noteDetailViewModel.discardNoteIfEmpty()
            navController.popBackStack()
        },
        isShowMenu = isShowMenu,
        isShowRemindDialog = isShowRemindDialog,
        isShowEditRemindDialog = isShowEditRemindDialog,
        editReminderDialogEvents = editReminderDialogEvents,
        snackbarHostState = snackbarHostState,
        scrollBehavior = scrollBehavior,
        menuEvents = menuEvents,
        topAppBarEvents = topAppBarEvents,
        note = note,
        reminderDialogEvents = reminderDialogEvents,
        onTitleTextChanged = { newText -> noteDetailViewModel.updateNoteTitle(newText) },
        onNoteTextChanged = { newText -> noteDetailViewModel.updateNoteContent(newText) },
        isShowShareDialog = isShowShareDialog,
        shareDialogEvents = shareDialogEvents,
        reminds = noteReminds,
        isShowLabelDialog = isShowLabelDialog,
        allLabels = allLabels,
        noteLabels = noteLabels,
        labelDialogEvents = labelDialogEvents,
        editableRemind = editableRemind
    )
}