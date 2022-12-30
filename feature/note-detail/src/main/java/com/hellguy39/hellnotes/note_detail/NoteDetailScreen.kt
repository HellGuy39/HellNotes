package com.hellguy39.hellnotes.note_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.components.FullScreenDialog
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.ColorParam
import com.hellguy39.hellnotes.note_detail.components.NoteDetailContent
import com.hellguy39.hellnotes.note_detail.components.NoteDetailTopAppBar
import com.hellguy39.hellnotes.note_detail.events.MenuEvents
import com.hellguy39.hellnotes.note_detail.events.TopAppBarEvents
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    note: Note,
    isShowMenu: Boolean,
    isShowRemindDialog: Boolean,
    isOpenColorDialog: Boolean,
    snackbarHostState: SnackbarHostState,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigationButtonClick: () -> Unit,
    onCloseRemindDialog: () -> Unit,
    menuEvents: MenuEvents,
    onTitleTextChanged: (text: String) -> Unit,
    onNoteTextChanged: (text: String) -> Unit,
    topAppBarEvents: TopAppBarEvents
) {
    BackHandler(onBack = onNavigationButtonClick)

    val containerColor = if (note.colorHex == ColorParam.DefaultColor)
        Color.Transparent
    else
        Color(note.colorHex)

    Scaffold(
        containerColor = containerColor,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            NoteDetailContent(
                innerPadding = innerPadding,
                note = note,
                onCloseRemindDialog = onCloseRemindDialog,
                isOpenRemindDialog = isShowRemindDialog,
                isOpenColorDialog = isOpenColorDialog,
                onTitleTextChanged = onTitleTextChanged,
                onNoteTextChanged = onNoteTextChanged
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NoteDetailScreenPreview() {
    NoteDetailScreen(
        note = Note(lastEditDate = Date().time),
        isShowMenu = false,
        isOpenColorDialog = false,
        isShowRemindDialog = false,
        onCloseRemindDialog = {},
        snackbarHostState = SnackbarHostState(),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        onNavigationButtonClick = {  },
        menuEvents = object : MenuEvents {
            override fun onDismissMenu() {}
            override fun onColor() {}
            override fun onLabels() {}
            override fun onShare() {}
            override fun onDelete() {}
        },
        onTitleTextChanged = {},
        onNoteTextChanged = {},
        topAppBarEvents = object : TopAppBarEvents {
            override fun onReminder() {}
            override fun onPin(isPinned: Boolean) {}
            override fun onColorSelected(colorHex: Long) {}
            override fun onMoreMenu() {}
        }
    )
}