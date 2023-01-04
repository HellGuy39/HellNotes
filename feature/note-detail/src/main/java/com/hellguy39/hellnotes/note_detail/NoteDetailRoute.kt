package com.hellguy39.hellnotes.note_detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.common.AlarmHelper
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.events.*
import com.hellguy39.hellnotes.note_detail.util.ShareHelper
import com.hellguy39.hellnotes.note_detail.util.ShareType
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val note by noteDetailViewModel.note.collectAsState()
    val reminds by noteDetailViewModel.reminds.collectAsState()
    val labels by noteDetailViewModel.labels.collectAsState()

    var isShowMenu by remember { mutableStateOf(false) }
    var isShowRemindDialog by remember { mutableStateOf(false) }
    var isShowShareDialog by remember { mutableStateOf(false) }
    var isShowLabelDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val isOpenColorDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val reminderDialogEvents = object : ReminderDialogEvents {
        override fun show() { isShowRemindDialog = true }
        override fun dismiss() { isShowRemindDialog = false }
        override fun onCreateRemind(remind: Remind) {
            noteDetailViewModel.insertRemind(remind)
            AlarmHelper.scheduleAlarm(context, remind)
        }
    }

    val labelDialogEvents = object : LabelDialogEvents {
        override fun show() { isShowLabelDialog = true }
        override fun dismiss() { isShowLabelDialog = false }
        override fun selectLabel(labelId: Long) { noteDetailViewModel.selectLabel(labelId) }
        override fun unselectLabel(labelId: Long) { noteDetailViewModel.unselectLabel(labelId) }
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
        override fun onReminder() { isShowRemindDialog = true }
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
        isOpenColorDialog = isOpenColorDialog,
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
        reminds = reminds,
        isShowLabelDialog = isShowLabelDialog,
        labels = labels,
        labelDialogEvents = labelDialogEvents
    )
}