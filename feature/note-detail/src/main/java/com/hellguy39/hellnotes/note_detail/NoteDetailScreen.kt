package com.hellguy39.hellnotes.note_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.model.util.ColorParam
import com.hellguy39.hellnotes.note_detail.components.*
import com.hellguy39.hellnotes.note_detail.events.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    note: Note,
    reminds: List<Remind>,
    isShowMenu: Boolean,
    isShowRemindDialog: Boolean,
    isShowShareDialog: Boolean,
    isShowLabelDialog: Boolean,
    isShowEditRemindDialog: Boolean,
    editReminderDialogEvents: EditReminderDialogEvents,
    labelDialogEvents: LabelDialogEvents,
    shareDialogEvents: ShareDialogEvents,
    snackbarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    noteLabels: List<Label>,
    allLabels: List<Label>,
    onNavigationButtonClick: () -> Unit,
    menuEvents: MenuEvents,
    onTitleTextChanged: (text: String) -> Unit,
    onNoteTextChanged: (text: String) -> Unit,
    topAppBarEvents: TopAppBarEvents,
    reminderDialogEvents: ReminderDialogEvents,
    editableRemind: Remind?
) {
    BackHandler(onBack = onNavigationButtonClick)

    val containerColor = if (note.colorHex == ColorParam.DefaultColor)
        Color.Transparent
    else
        Color(note.colorHex)

    LabelDialog(
        isShowDialog = isShowLabelDialog,
        noteLabels = noteLabels,
        allLabels = allLabels,
        note = note,
        events = labelDialogEvents
    )

    ShareDialog(
        isShowDialog = isShowShareDialog,
        events = shareDialogEvents,
        note = note
    )

    ReminderDialog(
        isShowDialog = isShowRemindDialog,
        events = reminderDialogEvents,
        note = note,
    )

    EditReminderDialog(
        note = note,
        isShowDialog = isShowEditRemindDialog,
        events = editReminderDialogEvents,
        remind = editableRemind
    )

    Scaffold(
        containerColor = containerColor,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->

            //val selectedLabels = labels.filter { note.labelIds.contains(it.id) }

            NoteDetailContent(
                innerPadding = innerPadding,
                note = note,
                reminds = reminds,
                noteLabels = noteLabels,
                onTitleTextChanged = onTitleTextChanged,
                onNoteTextChanged = onNoteTextChanged,
                labelDialogEvents = labelDialogEvents,
                editReminderDialogEvents = editReminderDialogEvents
            )
        },
        topBar = {
            NoteDetailTopAppBar(
                note = note,
                scrollBehavior = scrollBehavior,
                topAppBarEvents = topAppBarEvents,
                onNavigationButtonClick = onNavigationButtonClick,
                isShowMenu = isShowMenu,
                menuEvents = menuEvents
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    )
}