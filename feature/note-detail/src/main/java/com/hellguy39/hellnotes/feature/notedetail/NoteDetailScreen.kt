package com.hellguy39.hellnotes.feature.notedetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.feature.notedetail.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    snackbarHost: @Composable () -> Unit,
    noteDetailContentSelection: NoteDetailContentSelection,
    noteDetailChecklistSelection: NoteDetailChecklistSelection,
    uiState: NoteDetailUiState,
    topAppBarSelection: NoteDetailTopAppBarSelection,
    bottomBarSelection: NoteDetailBottomBarSelection,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val focusRequester = remember { FocusRequester() }

    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            if (uiState.wrapper.note.id != null) {
                NoteDetailContent(
                    innerPadding = innerPadding,
                    selection = noteDetailContentSelection,
                    checklistSelection = noteDetailChecklistSelection,
                    focusRequester = focusRequester,
                    uiState = uiState,
                    lazyListState = lazyListState,
                )
            }
        },
        topBar = {
            NoteDetailTopAppBar(
                scrollBehavior = scrollBehavior,
                topAppBarSelection = topAppBarSelection,
            )
        },
        bottomBar = {
            NoteDetailBottomBar(
                uiState = uiState,
                lazyListState = lazyListState,
                bottomBarSelection = bottomBarSelection,
            )
        },
        snackbarHost = snackbarHost,
    )
}

data class NoteDetailBottomBarSelection(
    val onMenu: () -> Unit,
    val onAttachment: () -> Unit,
)
