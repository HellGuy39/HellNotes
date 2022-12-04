package com.hellguy39.hellnotes.notes.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.*
import com.hellguy39.hellnotes.notes.list.components.NoteCard
import com.hellguy39.hellnotes.notes.list.components.NoteListDropdownMenu
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onSelectNoteClick: (Note) -> Unit,
    onUpdateListTypeButtonClick: () -> Unit,
    onFabAddClick:() -> Unit,
    onSettingsMenuItemClick: () -> Unit,
    onAboutAppMenuItemClick: () -> Unit,
    noteListUiState: NoteListUiState
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = "HellNotes", style = MaterialTheme.typography.headlineSmall) },
                actions = {
                    IconButton(
                        onClick = { onUpdateListTypeButtonClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.ListView),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.ViewType)
                        )
                    }
                    IconButton(
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.MoreVert),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.More)
                        )
                    }

                    NoteListDropdownMenu(
                        expanded = expanded,
                        onDismiss = { expanded = false },
                        onSettingsMenuItemClick = onSettingsMenuItemClick,
                        onAboutAppMenuItemClick = onAboutAppMenuItemClick
                    )
                },
            )
        },
        content = { innerPadding ->
            Crossfade(targetState = noteListUiState) { noteListUiState ->
                when(noteListUiState) {
                    is NoteListUiState.Success -> {
                        if (noteListUiState.listType == ListViewType.List) {
                            NoteList(
                                innerPadding = innerPadding,
                                uiState = noteListUiState,
                                onSelectNoteClick = onSelectNoteClick
                            )
                        } else {
                            NoteGrid(
                                innerPadding = innerPadding,
                                uiState = noteListUiState,
                                onSelectNoteClick = onSelectNoteClick
                            )
                        }
                    }
                    is NoteListUiState.Empty -> {

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.NoteAdd),
                                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote),
                                modifier = Modifier
                                    .width(128.dp)
                                    .height(128.dp)
                            )
                            Text(text = stringResource(id = HellNotesStrings.Text.Empty))
                        }

                    }
                    else -> Unit
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabAddClick() }
            ) {
                Icon(
                    painterResource(id = HellNotesIcons.Add),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote)
                )
            }
        },
    )
}

@Composable
fun NoteList(
    innerPadding: PaddingValues,
    uiState: NoteListUiState.Success,
    onSelectNoteClick: (Note) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        if (uiState.pinnedNotes.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Pinned),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            items(uiState.pinnedNotes) { note ->
                NoteCard(
                    note = note,
                    onClick = { onSelectNoteClick(note) }
                )
            }
            item {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Others),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        items(uiState.notes) { note ->
            NoteCard(
                note = note,
                onClick = { onSelectNoteClick(note) }
            )
        }
    }
}

@Composable
fun NoteGrid(
    innerPadding: PaddingValues,
    uiState: NoteListUiState.Success,
    onSelectNoteClick: (Note) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        columns = GridCells.Fixed(2)
    ) {
        if (uiState.pinnedNotes.isNotEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Pinned),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            items(
                items = uiState.pinnedNotes,
            ) { note ->
                NoteCard(
                    note = note,
                    onClick = { onSelectNoteClick(note) }
                )
            }
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Others),
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        items(items = uiState.notes) { note ->
            NoteCard(
                note = note,
                onClick = { onSelectNoteClick(note) }
            )
        }
    }

}