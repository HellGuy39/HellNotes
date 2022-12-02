package com.hellguy39.hellnotes.notes.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.*
import com.hellguy39.hellnotes.notes.util.NEW_NOTE_ID
import com.hellguy39.hellnotes.notes.util.navigateToNoteDetail
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteListRoute(
    navController: NavController,
    noteListViewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState by noteListViewModel.uiState.collectAsState()

    NoteListScreen(
        onSelectNoteClick = { note ->
            navController.navigateToNoteDetail(note.id)
        },
        onUpdateListTypeButtonClick = {
            noteListViewModel.updateListViewType()
        },
        onFabAddClick = {
            navController.navigateToNoteDetail(NEW_NOTE_ID)
        },
        noteListUiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onSelectNoteClick: (Note) -> Unit,
    onUpdateListTypeButtonClick: () -> Unit,
    onFabAddClick:() -> Unit,
    noteListUiState: NoteListUiState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "HellNotes")
                },
                actions = {
                    IconButton(
                        onClick = {
                            onUpdateListTypeButtonClick()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.ListView),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.ViewType)
                        )
                    }
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
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
        modifier = Modifier.padding(horizontal = 8.dp),
        contentPadding = innerPadding
    ) {
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
        modifier = Modifier.padding(horizontal = 8.dp),
        contentPadding = innerPadding,
        columns = GridCells.Fixed(2)
    ) {
        items(uiState.notes) { note ->
            NoteCard(
                note = note,
                onClick = { onSelectNoteClick(note) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
        ) {
            Text(
                text = note.title.toString(),
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 2
            )

            Text(
                text = note.note.toString(),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3
            )
        }
    }
}