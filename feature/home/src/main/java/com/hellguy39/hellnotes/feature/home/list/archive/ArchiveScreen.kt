package com.hellguy39.hellnotes.feature.home.list.archive

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.component.list.HNNotesList
import com.hellguy39.hellnotes.core.ui.component.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.component.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.list.archive.components.ArchiveTopAppBar
import com.hellguy39.hellnotes.feature.home.list.archive.components.ArchiveTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ArchiveScreen(
    uiState: ArchiveUiState,
    appBarSelection: ArchiveTopAppBarSelection,
    screenSelection: ArchiveScreenSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArchiveTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = appBarSelection,
                selectedNoteWrappers = screenSelection.selectedNoteWrappers
            )
        },
        content = { paddingValues ->
            when (uiState) {
                is ArchiveUiState.Idle -> Unit
                is ArchiveUiState.Empty -> {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(paddingValues)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.Archive),
                        message = stringResource(id = HellNotesStrings.Placeholder.Empty)
                    )
                }
                is ArchiveUiState.Success -> {
                    HNNotesList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp),
                        innerPadding = paddingValues,
                        noteSelection = screenSelection.noteSelection,
                        noteWrappers =  uiState.archivedNoteWrappers,
                        selectedNoteWrappers = screenSelection.selectedNoteWrappers,
                        listStyle = screenSelection.listStyle
                    )
                }
            }
        },
        snackbarHost = { CustomSnackbarHost(state = screenSelection.snackbarHostState) },
    )
}

data class ArchiveScreenSelection(
    val noteStyle: NoteStyle,
    val listStyle: ListStyle,
    val noteSelection: NoteSelection,
    val snackbarHostState: SnackbarHostState,
    val selectedNoteWrappers: List<NoteWrapper>
)