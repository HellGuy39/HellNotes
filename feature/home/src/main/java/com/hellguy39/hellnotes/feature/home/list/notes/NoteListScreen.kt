package com.hellguy39.hellnotes.feature.home.list.notes

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.component.list.HNNotesList
import com.hellguy39.hellnotes.core.ui.component.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.component.search.HNAdaptiveSearchBar
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.value.spacing
import com.hellguy39.hellnotes.core.ui.window.WindowInfo
import com.hellguy39.hellnotes.core.ui.window.rememberWindowInfo
import com.hellguy39.hellnotes.feature.home.list.notes.components.NoteListTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    uiState: NotesUiState,
    openedNoteId: Long,
    appBarSelection: NoteListTopAppBarSelection,
    screenSelection: NoteListScreenSelection,
    noteListViewModel: NoteListViewModel
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val lazyListState = rememberLazyListState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val windowInfo = rememberWindowInfo()

    Scaffold(
        topBar = {
            HNAdaptiveSearchBar(
                modifier = Modifier.fillMaxWidth(),
                horizontalPadding = MaterialTheme.spacing.small,
                query = uiState.searchState.query,
                onQueryChange = noteListViewModel::searchQueryChange,
                onSearch = noteListViewModel::search,
                active = uiState.searchState.isActive,
                onActiveChange = noteListViewModel::searchIsActiveChange,
                placeholder = { Text(text = stringResource(id = HellNotesStrings.Hint.Search)) },
                leadingIcon = {
                    if (uiState.searchState.isActive) {
                        IconButton(onClick = { noteListViewModel.searchIsActiveChange(false) }) {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.ArrowBack),
                                contentDescription = null
                            )
                        }
                    } else {
                        if (windowInfo.screenWidthInfo == WindowInfo.WindowType.Compact) {
                            IconButton(onClick = {  }) {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Menu),
                                    contentDescription = null
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.Search),
                                contentDescription = null
                            )
                        }
                    }
                }
            ) {
                LazyColumn {
                    items(5) {
                        Text("Search")
                    }
                }
            }
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
                        openedNoteId = openedNoteId,
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
        }
    )
}

data class NoteListScreenSelection(
    val snackbarHostState: SnackbarHostState,
    val noteSelection: NoteSelection,
    val listStyle: ListStyle,
    val selectedNoteWrappers: List<NoteWrapper>,
)