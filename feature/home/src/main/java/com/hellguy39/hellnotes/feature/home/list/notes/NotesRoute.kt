package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.feature.home.MainUiEvent
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.list.notes.components.NoteListTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesRoute(
    mainViewModel: MainViewModel,
    windowWidthSize: WindowWidthSizeClass,
    onDrawerOpen: (Boolean) -> Unit,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    val uiState by notesViewModel.uiState.collectAsStateWithLifecycle()
    val openedNoteId by mainViewModel.openedNoteId.collectAsStateWithLifecycle()

    NoteListScreen(
        uiState = uiState,
        openedNoteId = openedNoteId,
        onDrawerOpen = onDrawerOpen,
        windowWidthSize = windowWidthSize,
        notesViewModel = notesViewModel,
        noteSelection = NoteSelection(
            noteStyle = NoteStyle.Outlined,
            isSwipeable = false,
            onClick = { noteWrapper ->
                noteWrapper.note.id?.let { id ->
                    mainViewModel.onEvent(MainUiEvent.OpenNoteEditing(id))
                }
            },
            onLongClick = { noteWrapper -> },
            onDismiss = { dismissDirection, noteWrapper -> false }
        ),
        onOpenSearchBar = { isOpen ->
            mainViewModel.onEvent(MainUiEvent.OpenSearchBar(isOpen))
        }
    )
}