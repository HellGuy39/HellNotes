package com.hellguy39.hellnotes.notes.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.util.NEW_NOTE_ID
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteDetailRoute(
    navController: NavController,
    noteDetailViewModel: NoteDetailViewModel = hiltViewModel(),
) {
//    noteId?.let { id ->
//        if (id != NEW_NOTE_ID)
//            noteDetailViewModel.fetchNote(id)
//    }

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
        uiState = uiState,
        onPinButtonClick = {

        },
        onLabelButtonClick = {

        },
        onDeleteButtonClick = { noteId ->
            if (noteId != NEW_NOTE_ID) {
                noteDetailViewModel.deleteNoteById(noteId)
            }

            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    uiState: NoteDetailUiState,
    onNavigationButtonClick: (note: Note) -> Unit,
    onLabelButtonClick: () -> Unit,
    onPinButtonClick: () -> Unit,
    onDeleteButtonClick: (noteId: Int) -> Unit
) {
    var id by remember { mutableStateOf(NEW_NOTE_ID) }
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isPinned by remember { mutableStateOf(false) }
    var labels by remember { mutableStateOf(listOf<String>()) }

    when(uiState) {
        is NoteDetailUiState.Success -> {
            id = uiState.note.id ?: NEW_NOTE_ID
            title = uiState.note.title.toString()
            note = uiState.note.note.toString()
            isPinned = uiState.note.isPinned == true
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
                OutlinedTextField(
                    value = title,
                    onValueChange = { newText ->
                        title = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = {
                        Text(stringResource(id = HellNotesStrings.Hint.Title))
                    },
                )
                OutlinedTextField(
                    value = note,
                    onValueChange = { newText ->
                        note = newText
                    },
                    placeholder = {
                        Text(stringResource(id = HellNotesStrings.Hint.Note))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyRow {
                    items(labels) { label ->
                        ElevatedAssistChip(
                            onClick = { /* Do something! */ },
                            label = { Text(label) },
                        )
                    }
                }
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
                                    note = note,
                                    lastEditDate = 0,
                                    isPinned = false,
                                    labels = listOf()
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Palette),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Palette)
                        )
                    }
                    IconButton(
                        onClick = {
                            isPinned = !isPinned
                            onPinButtonClick()
                        }
                    ) {
                        Icon(
                            painter = if (isPinned)
                                painterResource(id = HellNotesIcons.PinActivated)
                            else
                                painterResource(id = HellNotesIcons.PinDisabled),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Pin)
                        )
                    }
                    IconButton(
                        onClick = { onLabelButtonClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Label),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Labels)
                        )
                    }
                    IconButton(
                        onClick = {
                            onDeleteButtonClick(id)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                        )
                    }
                }
            )
        }
    )
}