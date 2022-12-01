package com.hellguy39.hellnotes.notes.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.NoteDetailUiState
import com.hellguy39.hellnotes.notes.NoteDetailViewModel
import com.hellguy39.hellnotes.notes.util.NEW_NOTE_ID

@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
    noteId: Int?
) {
    noteId?.let { id ->
        if (id != NEW_NOTE_ID)
            noteDetailViewModel.fetchNote(id)
    }

    val uiState by noteDetailViewModel.uiState.collectAsState()

    NoteDetailScreen(
        onNavigationButtonClick = { note ->

            if (note.id == NEW_NOTE_ID) {
                noteDetailViewModel.insertNote(note.copy(id = null))
            } else {
                noteDetailViewModel.updateNote(note)
            }

            navController.popBackStack()
        },
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    uiState: NoteDetailUiState,
    onNavigationButtonClick: (note: Note) -> Unit
) {
    var id by remember { mutableStateOf(NEW_NOTE_ID) }
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    when(uiState) {
        is NoteDetailUiState.Success -> {
            id = uiState.note.id ?: NEW_NOTE_ID
            title = uiState.note.title.toString()
            note = uiState.note.note.toString()
        }
        is NoteDetailUiState.Empty -> {

        }
        else -> Unit
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                TextField(
                    value = title,
                    onValueChange = { newText ->
                        title = newText
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = note,
                    onValueChange = { newText ->
                        note = newText
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
//                    Text(
//                        "Edit note",
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigationButtonClick(
                                Note(
                                    id = id,
                                    title = title,
                                    note = note
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Menu"
                        )
                    }
                },
//                actions = {
//                    IconButton(
//                        onClick = { onEditActionButtonClick() }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.Edit,
//                            contentDescription = "Edit note"
//                        )
//                    }
//                }
            )
        }
    )
}

