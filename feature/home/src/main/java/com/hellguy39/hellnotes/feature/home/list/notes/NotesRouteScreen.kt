package com.hellguy39.hellnotes.feature.home.list.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.component.list.HNNotesList
import com.hellguy39.hellnotes.core.ui.component.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.component.search.HNAdaptiveSearchBar
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.value.spacing
import com.hellguy39.hellnotes.core.ui.window.isCompact

@Composable
fun NoteListScreen(
    uiState: NotesUiState,
    openedNoteId: Long,
    windowWidthSize: WindowWidthSizeClass,
    noteSelection: NoteSelection,
    onDrawerOpen: (Boolean) -> Unit,
    notesViewModel: NotesViewModel,
    onOpenSearchBar: (Boolean) -> Unit
) {
    Scaffold() { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
            HNAdaptiveSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = -1f },
                horizontalPadding = MaterialTheme.spacing.small,
                query = uiState.searchState.query,
                onQueryChange = { text -> notesViewModel.onEvent(NotesUiEvent.ChangeSearchQuery(text)) },
                onSearch = { text -> notesViewModel.onEvent(NotesUiEvent.Search(text)) },
                active = uiState.searchState.isActive,
                onActiveChange = { isActive ->
                    onOpenSearchBar(isActive)
                    notesViewModel.onEvent(NotesUiEvent.ChangeIsSearchActive(isActive))
                },
                windowWidthSize = windowWidthSize,
                placeholder = { Text(text = stringResource(id = HellNotesStrings.Hint.Search)) },
                leadingIcon = {
                    if (uiState.searchState.isActive) {
                        IconButton(
                            onClick = {
                                onOpenSearchBar(false)
                                notesViewModel.onEvent(NotesUiEvent.ChangeIsSearchActive(false))
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.ArrowBack),
                                contentDescription = null
                            )
                        }
                    } else {
                        if (windowWidthSize.isCompact()) {
                            IconButton(onClick = { onDrawerOpen(true) }) {
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
                    items(10) {
                        ListItem(headlineContent = { Text("Item $it") })
                    }
                }
            }

            when(uiState.notesListState) {
                is NotesListState.Idle -> Unit
                is NotesListState.Empty -> {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp)
                            .padding(innerPadding),
                        heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                        message = stringResource(id = HellNotesStrings.Placeholder.Empty)
                    )
                }
                is NotesListState.Success -> {
                    HNNotesList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 64.dp)
                            .padding(horizontal = 4.dp),
                        openedNoteId = openedNoteId,
                        innerPadding = innerPadding,
                        noteSelection = noteSelection,
                        pinnedNoteWrappers = uiState.notesListState.pinnedNoteWrappers,
                        unpinnedNoteWrappers = uiState.notesListState.unpinnedNoteWrappers,
                        selectedNoteWrappers = listOf(), // TODO: Implement selected notes
                        listStyle = ListStyle.Column, // TODO: Make listStyle mutable
                    )
                }
            }
        }
    }
}
