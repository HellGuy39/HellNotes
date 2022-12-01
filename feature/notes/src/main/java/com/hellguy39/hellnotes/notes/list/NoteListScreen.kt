package com.hellguy39.hellnotes.notes.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.*
import com.hellguy39.hellnotes.notes.util.NEW_NOTE_ID
import com.hellguy39.hellnotes.notes.util.navigateToNoteDetail

@Composable
fun NoteListRoute(
    navController: NavController,
    noteListViewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState by noteListViewModel.uiState.collectAsState()

    noteListViewModel.fetchNotes()

    NoteListScreen(
        onSelectNoteClick = { note ->
            navController.navigateToNoteDetail(note.id)
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
    onFabAddClick:() -> Unit,
    noteListUiState: NoteListUiState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->

            when(noteListUiState) {
                is NoteListUiState.Success -> {

                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        contentPadding = innerPadding
                    ) {
                        items(noteListUiState.notes) { note ->
                            NoteCard(
                                note = note,
                                onClick = { onSelectNoteClick(note) }
                            )
                        }
                    }
                }
                else -> Unit
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabAddClick() }
            ) {
                Icon(Icons.Filled.Add, "Add new note")
            }
        }
    )
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
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            Text(
                text = note.title.toString(),
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = note.note.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}