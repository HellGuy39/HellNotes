package com.hellguy39.hellnotes.feature.home.list.trash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.list.trash.components.TrashTopAppBar
import com.hellguy39.hellnotes.feature.home.list.trash.components.TrashTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TrashScreen(
    uiState: TrashUiState,
    screenSelection: TrashScreenSelection,
    appBarSelection: TrashTopAppBarSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TrashTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = appBarSelection,
                selectedNoteWrappers = screenSelection.selectedNoteWrappers
            )
        },
        snackbarHost = {},
        content = { paddingValues ->
            when(uiState) {
                is TrashUiState.Idle -> Unit
                is TrashUiState.Empty -> {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(paddingValues)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.Delete),
                        message = stringResource(id = HellNotesStrings.Placeholder.NoNotesInTrash)
                    )
                }
                is TrashUiState.Success -> {
                    HNNotesList(
                        modifier = Modifier.fillMaxSize()
                            .padding(4.dp),
                        innerPadding = paddingValues,
                        noteSelection = screenSelection.noteSelection,
                        noteWrappers = uiState.trashedNotes,
                        selectedNoteWrappers = screenSelection.selectedNoteWrappers,
                        listStyle = screenSelection.listStyle,
//                        listHeader = {
//                            TipCard(
//                                isVisible = true,
//                                message = stringResource(id = HellNotesStrings.Tip.AutoDeleteTrash),
//                                onClose = {
//                                    //trashViewModel.trashTipCompleted(true)
//                                }
//                            )
//                        },
                    )
                }
            }
        }
    )
}

data class TrashScreenSelection(
    val listStyle: ListStyle,
    val noteSelection: NoteSelection,
    val noteStyle: NoteStyle,
    val selectedNoteWrappers: List<NoteWrapper>
)