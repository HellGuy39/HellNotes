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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    note: Note,
    onNavigationButtonClick: () -> Unit,
    onLabelButtonClick: () -> Unit,
    onPinButtonClick: (isPinned: Boolean) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onTitleTextChanged: (text: String) -> Unit,
    onNoteTextChanged: (text: String) -> Unit,
) {

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
                    value = note.title,
                    onValueChange = { newText -> onTitleTextChanged(newText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = {
                        Text(stringResource(id = HellNotesStrings.Hint.Title))
                    },
                )
                OutlinedTextField(
                    value = note.note,
                    onValueChange = { newText -> onNoteTextChanged(newText) },
                    placeholder = {
                        Text(stringResource(id = HellNotesStrings.Hint.Note))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyRow {
                    items(note.labels) { label ->
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
                title = { /*Text( "Edit note", maxLines = 1, overflow = TextOverflow.Ellipsis)*/ },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigationButtonClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.ArrowBack),
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
                        onClick = { onPinButtonClick(!note.isPinned) }
                    ) {
                        Icon(
                            painter = if (note.isPinned)
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
                        onClick = { onDeleteButtonClick() }
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