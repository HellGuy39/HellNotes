package com.hellguy39.hellnotes.notes.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.detail.components.NoteDetailDropdownMenu
import com.hellguy39.hellnotes.notes.util.formatAsLastEditDate
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
    var expanded by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    BackHandler(onBack = onNavigationButtonClick)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                Text(
                    text = stringResource(
                        id = HellNotesStrings.Text.Edited,
                        formatArgs = arrayOf(note.lastEditDate.formatAsLastEditDate())
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
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
                            painter = painterResource(id = HellNotesIcons.Notifications),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Reminder)
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
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.MoreVert),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.More)
                        )
                    }

                    NoteDetailDropdownMenu(
                        expanded = expanded,
                        onDismiss = { expanded = false },
                        onColorItemClick = {  },
                        onLabelsItemClick = {  },
                        onDeleteItemClick = onDeleteButtonClick
                    )
                }
            )
        }
    )
}