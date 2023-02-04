package com.hellguy39.hellnotes.feature.note_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.model.isNoteValid
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.feature.note_detail.components.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    snackbarHostState: SnackbarHostState,
    noteDetailContentSelection: NoteDetailContentSelection,
    noteDetailDropdownMenuSelection: NoteDetailDropdownMenuSelection,
    uiState: NoteDetailUiState,
    noteDetailTopAppBarSelection: NoteDetailTopAppBarSelection,
    dateHelper: DateHelper
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = uiState.isLoading) {
        uiState.let {
            if (!uiState.note.isNoteValid() && !uiState.isLoading) {
                focusRequester.requestFocus()
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            NoteDetailContent(
                innerPadding = innerPadding,
                selection = noteDetailContentSelection,
                dateHelper = dateHelper,
                focusRequester = focusRequester,
                uiState = uiState
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