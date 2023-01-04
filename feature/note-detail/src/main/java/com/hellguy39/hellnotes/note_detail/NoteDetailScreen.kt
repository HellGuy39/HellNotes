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
    isOpenColorDialog: Boolean,
    isShowShareDialog: Boolean,
    isShowLabelDialog: Boolean,
    labelDialogEvents: LabelDialogEvents,
    shareDialogEvents: ShareDialogEvents,
    snackbarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    labels: List<Label>,
    onNavigationButtonClick: () -> Unit,
    menuEvents: MenuEvents,
    onTitleTextChanged: (text: String) -> Unit,
    onNoteTextChanged: (text: String) -> Unit,
    topAppBarEvents: TopAppBarEvents,
    reminderDialogEvents: ReminderDialogEvents
) {
    BackHandler(onBack = onNavigationButtonClick)

    val containerColor = if (note.colorHex == ColorParam.DefaultColor)
        Color.Transparent
    else
        Color(note.colorHex)

    LabelDialog(
        isShowDialog = isShowLabelDialog,
        labels = labels,
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
        note = note
    )

    Scaffold(
        containerColor = containerColor,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->

            val selectedLabels = labels.filter { note.labelIds.contains(it.id) }

            NoteDetailContent(
                innerPadding = innerPadding,
                note = note,
                reminds = reminds,
                labels = selectedLabels,
                onTitleTextChanged = onTitleTextChanged,
                onNoteTextChanged = onNoteTextChanged,
                labelDialogEvents = labelDialogEvents
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
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
//@Composable
//fun NoteDetailScreenPreview() {
//    NoteDetailScreen(
//        note = Note(lastEditDate = Date().time),
//        isShowMenu = false,
//        isOpenColorDialog = false,
//        isShowRemindDialog = false,
//        reminderDialogEvents = object : ReminderDialogEvents{
//            override fun show() {}
//            override fun dismiss() {}
//            override fun onCreateRemind(remind: Remind) {}
//        },
//        snackbarHostState = SnackbarHostState(),
//        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
//        onNavigationButtonClick = {  },
//        menuEvents = object : MenuEvents {
//            override fun onDismissMenu() {}
//            override fun onColor() {}
//            override fun onLabels() {}
//            override fun onShare() {}
//            override fun onDelete() {}
//        },
//        onTitleTextChanged = {},
//        onNoteTextChanged = {},
//        topAppBarEvents = object : TopAppBarEvents {
//            override fun onReminder() {}
//            override fun onPin(isPinned: Boolean) {}
//            override fun onColorSelected(colorHex: Long) {}
//            override fun onMoreMenu() {}
//        },
//        shareDialogEvents = object: ShareDialogEvents {
//            override fun show() {}
//            override fun dismiss() {}
//            override fun shareAsTxtFile(note: Note) {}
//            override fun shareAsPlainText(note: Note) {}
//        },
//        reminds = listOf(),
//        isShowShareDialog = false,
//        isShowLabelDialog = false,
//
//    )
//}