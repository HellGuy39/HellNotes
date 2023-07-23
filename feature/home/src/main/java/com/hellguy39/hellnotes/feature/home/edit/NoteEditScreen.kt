package com.hellguy39.hellnotes.feature.home.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.feature.home.edit.components.NoteDetailChecklistSelection
import com.hellguy39.hellnotes.feature.home.edit.components.NoteDetailContentSelection
import com.hellguy39.hellnotes.feature.home.edit.components.NoteEditBottomBar
import com.hellguy39.hellnotes.feature.home.edit.components.NoteEditBottomBarSelection
import com.hellguy39.hellnotes.feature.home.edit.components.NoteEditContent
import com.hellguy39.hellnotes.feature.home.edit.components.NoteEditTopBar
import com.hellguy39.hellnotes.feature.home.edit.components.NoteEditTopBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    contentType: HNContentType,
    snackbarHost: @Composable () -> Unit,
    uiState: NoteDetailUiState,
    noteEditViewModel: NoteEditViewModel,
    onCloseNoteEdit: () -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteEditTopBar(
                noteWrapperState = uiState.noteWrapperState,
                scrollBehavior = scrollBehavior,
                topAppBarSelection = NoteEditTopBarSelection(
                    onNavigationButtonClick = {
                        noteEditViewModel.send(NoteEditUiEvent.CloseNote)
                        onCloseNoteEdit()
                    },
                    onPin = {},
                    onArchive = {},
                    onReminder = {}
                )
            )
        },
        content = { innerPadding ->
            when (uiState.noteWrapperState) {
                is NoteWrapperState.Success -> {
                    NoteEditContent(
                        innerPadding = innerPadding,
                        listState = listState,
                        contentSelection = NoteDetailContentSelection(
                            onTitleTextChanged = { text ->
                                noteEditViewModel.send(NoteEditUiEvent.EditNoteTitle(text))
                            },
                            onNoteTextChanged = { text ->
                                noteEditViewModel.send(NoteEditUiEvent.EditNoteContent(text))
                            },
                            onReminderClick = { reminder -> },
                            onLabelClick = { label -> },
                        ),
                        checklistSelection = NoteDetailChecklistSelection(
                            onCheckedChange = { checklist, checklistItem, b -> },
                            onAddChecklistItem = { checklist -> },
                            onDeleteChecklistItem = { checklist, checklistItem -> },
                            onChecklistNameChange = { checklist, s -> },
                            onUpdateChecklistItemText = { checklist, checklistItem, s -> },
                            onUpdateIsChecklistExpanded = { checklist, b -> },
                            onDoneAll = { checklist -> },
                            onRemoveDone = { checklist -> },
                            onDelete = { checklist -> },
                        ),
                        noteWrapperState = uiState.noteWrapperState,
                    )
                }

                is NoteWrapperState.Empty -> Unit
            }
        },
        bottomBar = {
            NoteEditBottomBar(
                noteWrapperState = uiState.noteWrapperState,
                lazyListState = listState,
                bottomBarSelection = NoteEditBottomBarSelection(
                    onMenu = {
                        noteEditViewModel.send(NoteEditUiEvent.OpenMenuBottomSheet(true))
                    },
                    onAttachment = {
                        noteEditViewModel.send(NoteEditUiEvent.OpenAttachmentBottomSheet(true))
                    }
                )
            )
        },
        snackbarHost = snackbarHost,
    )
}