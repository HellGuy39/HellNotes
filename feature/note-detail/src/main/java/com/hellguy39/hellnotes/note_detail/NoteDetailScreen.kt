package com.hellguy39.hellnotes.note_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.note_detail.components.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    snackbarHostState: SnackbarHostState,
    noteDetailContentSelection: NoteDetailContentSelection,
    noteDetailDropdownMenuSelection: NoteDetailDropdownMenuSelection,
    noteDetailTopAppBarSelection: NoteDetailTopAppBarSelection
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            NoteDetailContent(
                innerPadding = innerPadding,
                selection = noteDetailContentSelection
            )
        },
        topBar = {
            NoteDetailTopAppBar(
                scrollBehavior = scrollBehavior,
                topAppBarSelection = noteDetailTopAppBarSelection,
                dropdownMenuSelection = noteDetailDropdownMenuSelection
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    )
}