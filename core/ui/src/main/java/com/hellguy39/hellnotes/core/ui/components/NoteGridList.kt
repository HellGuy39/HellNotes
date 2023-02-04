package com.hellguy39.hellnotes.core.ui.components

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
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun NoteGridList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection,
    pinnedNotes: List<NoteDetailWrapper>,
    unpinnedNotes: List<NoteDetailWrapper>,
    selectedNotes: List<Note>,
    listHeader: @Composable () -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        columns = GridCells.Adaptive(192.dp),
        contentPadding = innerPadding
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            listHeader()
        }
        if (pinnedNotes.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(id = HellNotesStrings.Label.Pinned),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            items(pinnedNotes) { wrapper ->
                NoteCard(
                    note = wrapper.note,
                    selection = noteSelection,
                    isSelected = selectedNotes.contains(wrapper.note),
                    labels = wrapper.labels,
                    reminds = wrapper.reminders
                )
            }
            if (unpinnedNotes.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Label.Others),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        items(unpinnedNotes) { wrapper ->
            NoteCard(
                note = wrapper.note,
                selection = noteSelection,
                isSelected = selectedNotes.contains(wrapper.note),
                labels = wrapper.labels,
                reminds = wrapper.reminders
            )
        }
    }

}