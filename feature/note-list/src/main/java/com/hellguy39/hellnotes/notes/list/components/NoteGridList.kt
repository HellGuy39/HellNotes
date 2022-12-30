package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.notes.list.NoteListUiState
import com.hellguy39.hellnotes.notes.list.events.NoteEvents
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteGridList(
    innerPadding: PaddingValues,
    uiState: NoteListUiState.Success,
    noteEvents: NoteEvents
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        columns = GridCells.Adaptive(192.dp)//Fixed(2)
    ) {
        if (uiState.pinnedNotes.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Pinned),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            items(
                items = uiState.pinnedNotes,
            ) { note ->
                NoteCard(
                    note = note,
                    onClick = { noteEvents.onClick(note) },
                    onLongClick = { noteEvents.onLongClick(note) }
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Others),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        items(items = uiState.notes) { note ->
            NoteCard(
                note = note,
                onClick = { noteEvents.onClick(note) },
                onLongClick = { noteEvents.onLongClick(note) }
            )
        }
    }

}