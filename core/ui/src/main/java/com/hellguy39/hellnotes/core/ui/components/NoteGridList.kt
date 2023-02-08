package com.hellguy39.hellnotes.core.ui.components

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteGridList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection,
    categories: List<NoteCategory>,
    selectedNotes: List<Note>,
    listHeader: @Composable () -> Unit = {}
) {
    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Adaptive(minSize = 192.dp)
    } else StaggeredGridCells.Fixed(2)

    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        contentPadding = innerPadding,
        columns = cellConfiguration
    ) {
        item(
            span = StaggeredGridItemSpan.FullLine
        ) {
            listHeader()
        }
        categories.forEach { category ->
            if (category.notes.isNotEmpty()) {
                if (category.title.isNotEmpty()) {
                    item(
                        span = StaggeredGridItemSpan.FullLine
                    ) {
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

data class NoteCategory(
    val title: String = "",
    val notes: List<NoteDetailWrapper> = listOf()
)