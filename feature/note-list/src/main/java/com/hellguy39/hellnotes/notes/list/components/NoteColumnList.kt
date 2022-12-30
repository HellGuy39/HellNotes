package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.model.util.Sorting
import com.hellguy39.hellnotes.notes.list.NoteListUiState
import com.hellguy39.hellnotes.notes.list.events.NoteEvents
import com.hellguy39.hellnotes.notes.list.events.SortMenuEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteColumnList(
    innerPadding: PaddingValues,
    uiState: NoteListUiState.Success,
    noteEvents: NoteEvents,
    isShowSortMenu: Boolean,
    sortMenuEvents: SortMenuEvents,
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
                isShowSortMenu = isShowSortMenu
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

@Composable
fun ListConfiguration(
    uiState: NoteListUiState.Success,
    sortMenuEvents: SortMenuEvents,
    isShowSortMenu: Boolean
) {
    val sortName = when(uiState.sorting) {
        is Sorting.DateOfLastEdit -> stringResource(id = HellNotesStrings.Text.DateOfLastEdit)
        is Sorting.DateOfCreation -> stringResource(id = HellNotesStrings.Text.DateOfCreation)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        //horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = { sortMenuEvents.show() },
        ) {
            Row {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Sort),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Sort)
                )
                Text(
                    text = stringResource(id = HellNotesStrings.Text.SortBy, sortName),
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                SortDropdownMenu(
                    expanded = isShowSortMenu,
                    events = sortMenuEvents,
                    currentSorting = uiState.sorting
                )
            }
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(id = HellNotesIcons.GridView),
                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.ViewType)
            )
        }
    }
}