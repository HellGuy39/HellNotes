package com.hellguy39.hellnotes.feature.home.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.component.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.core.ui.value.LocalElevation
import com.hellguy39.hellnotes.core.ui.value.elevation
import com.hellguy39.hellnotes.core.ui.value.spacing
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
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

    val elevation = LocalElevation.current
    val contentType = rememberContentType()

    val startElevation by remember {
        mutableStateOf(if (contentType == HNContentType.DualPane) elevation.level1 else elevation.level0)
    }
    val endElevation by remember {
        mutableStateOf(if (contentType == HNContentType.DualPane) elevation.level3 else elevation.level2)
    }

    val containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(startElevation)
    val scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(endElevation)

    val modifier = if (contentType == HNContentType.DualPane)
        Modifier
            .padding(
                bottom = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.medium
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .clip(MaterialTheme.shapes.extraLarge)
    else Modifier

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .then(modifier),
        containerColor = containerColor,
        topBar = {
            NoteEditTopBar(
                noteWrapperState = uiState.noteWrapperState,
                scrollBehavior = scrollBehavior,
                contentType = contentType,
                containerColor = containerColor,
                scrolledContainerColor = scrolledContainerColor,
                topAppBarSelection = NoteEditTopBarSelection(
                    onNavigationButtonClick = {
                        noteEditViewModel.send(NoteEditUiEvent.CloseNote)
                        onCloseNoteEdit()
                    },
                    onPin = {
                        noteEditViewModel.send(NoteEditUiEvent.UpdateIsPinned)
                    },
                    onArchive = {
                        noteEditViewModel.send(NoteEditUiEvent.UpdateIsArchived)
                    },
                    onReminder = {
                        //noteEditViewModel.send(NoteEditUiEvent.)
                    }
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
                            onReminderClick = { reminder ->  },
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

                is NoteWrapperState.Empty -> {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        message = "Select note for start editing"
                    )
                }
            }
        },
        bottomBar = {
            NoteEditBottomBar(
                noteWrapperState = uiState.noteWrapperState,
                lazyListState = listState,
                contentType = contentType,
                containerColor = containerColor,
                scrolledContainerColor = scrolledContainerColor,
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