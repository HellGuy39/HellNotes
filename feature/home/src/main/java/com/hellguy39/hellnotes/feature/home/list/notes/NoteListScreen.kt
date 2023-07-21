package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.component.list.HNNotesList
import com.hellguy39.hellnotes.core.ui.component.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.component.top_bar.HNTopAppBar
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.list.notes.components.NoteListTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    uiState: NotesUiState,
    appBarSelection: NoteListTopAppBarSelection,
    screenSelection: NoteListScreenSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val lazyListState = rememberLazyListState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
             HNTopAppBar(
                 scrollBehavior = scrollBehavior,
                 title = stringResource(id = HellNotesStrings.Title.Notes)
             )
        },
        content = { innerPadding ->
            when(uiState.listState) {
                is NoteListState.Idle -> Unit
                is NoteListState.Empty -> {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(innerPadding)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                        message = stringResource(id = HellNotesStrings.Placeholder.Empty)
                    )
                }
                is NoteListState.Success -> {
                    HNNotesList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp),
                        innerPadding = innerPadding,
                        noteSelection = screenSelection.noteSelection,
                        pinnedNoteWrappers = uiState.listState.pinnedNoteWrappers,
                        unpinnedNoteWrappers = uiState.listState.unpinnedNoteWrappers,
                        selectedNoteWrappers = screenSelection.selectedNoteWrappers,
                        listStyle = screenSelection.listStyle,
                        lazyListState = lazyListState,
                        lazyStaggeredGridState = lazyStaggeredGridState
                    )
                }
            }
        },
//        floatingActionButton = {
//
//            val isFabExpanded by derivedStateOf {
//                if (screenSelection.listStyle == ListStyle.Column)
//                    lazyListState.firstVisibleItemIndex == 0
//                else
//                    lazyStaggeredGridState.firstVisibleItemIndex == 0
//            }
//
//            ExtendedFloatingActionButton(
//                text = {
//                    Text(
//                        text = "New note"
//                    )
//                },
//                icon = {
//                    Icon(
//                        painter = painterResource(id = HellNotesIcons.Add),
//                        contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote)
//                    )
//                },
//                onClick = screenSelection.onAddNote,
//                expanded = isFabExpanded
//            )
//        },
//        snackbarHost = { CustomSnackbarHost(state = screenSelection.snackbarHostState) }
    )
}

data class NoteListScreenSelection(
    val snackbarHostState: SnackbarHostState,
    val noteSelection: NoteSelection,
    val listStyle: ListStyle,
    val selectedNoteWrappers: List<NoteWrapper>,
    val onAddNote: () -> Unit,
)