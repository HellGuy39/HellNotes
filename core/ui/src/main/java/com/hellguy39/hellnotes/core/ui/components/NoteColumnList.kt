package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
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
fun NoteColumnList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection,
    categories: List<NoteCategory>,
    selectedNotes: List<Note>,
    listHeader: @Composable () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxSize(),
        contentPadding = innerPadding
    ) {
        item {
            listHeader()
        }

        categories.forEach { category ->
            if (category.notes.isNotEmpty()) {
                if (category.title.isNotEmpty()) {
                    item {
                        Text(
                            text = category.title,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                items(
                    items = category.notes,
                    key = { it.note.id ?: 0 },
                ) { wrapper ->
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
    }
}