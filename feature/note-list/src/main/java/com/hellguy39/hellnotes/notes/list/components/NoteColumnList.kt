package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.NoteCard
import com.hellguy39.hellnotes.notes.list.NoteListUiState
import com.hellguy39.hellnotes.notes.list.events.NoteEvents
import com.hellguy39.hellnotes.notes.list.events.SortMenuEvents
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteColumnList(
    innerPadding: PaddingValues,
    uiState: NoteListUiState.Success,
    noteEvents: NoteEvents,
    isShowSortMenu: Boolean,
    sortMenuEvents: SortMenuEvents,
    onListStyleChange: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxSize(),
        contentPadding = innerPadding
    ) {
        item {
            ListConfiguration(
                sortMenuEvents = sortMenuEvents,
                uiState = uiState,
                isShowSortMenu = isShowSortMenu,
                onListStyleChange = onListStyleChange
            )
        }
        if (uiState.pinnedNotes.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Pinned),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            items(uiState.pinnedNotes) { note ->
                NoteCard(
                    note = note,
                    onClick = { noteEvents.onClick(note) },
                    onLongClick = { noteEvents.onLongClick(note) },
                    isSelected = uiState.selectedNotes.contains(note)
                )
            }
            item {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Others),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        items(uiState.notes) { note ->
            NoteCard(
                note = note,
                onClick = { noteEvents.onClick(note) },
                onLongClick = { noteEvents.onLongClick(note) },
                isSelected = uiState.selectedNotes.contains(note)
            )
        }
    }
}