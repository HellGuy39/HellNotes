package com.hellguy39.hellnotes.core.ui.components.list

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.SwipeableNoteCard
import com.hellguy39.hellnotes.core.ui.isSingleList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteGridList(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteStyle: NoteStyle,
    isSwipeable: Boolean,
    onClick: (Note) -> Unit,
    onLongClick: (Note) -> Unit,
    onDismiss: (DismissDirection, Note) -> Boolean,
    categories: List<NoteCategory>,
    selectedNotes: List<Note> = listOf(),
    listHeader: @Composable () -> Unit = {},
) {
    val cellConfiguration =
        if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 192.dp)
        } else {
            StaggeredGridCells.Fixed(2)
        }

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        contentPadding = innerPadding,
        columns = cellConfiguration,
    ) {
        item(
            span = StaggeredGridItemSpan.FullLine,
        ) {
            listHeader()
        }
        categories.forEach { category ->
            if (category.notes.isNotEmpty()) {
                if (category.title.isNotEmpty() && !categories.isSingleList()) {
                    item(
                        span = StaggeredGridItemSpan.FullLine,
                    ) {
                        Text(
                            text = category.title,
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
                items(
                    items = category.notes,
                    key = { note -> note.note.id ?: 0 },
                ) { wrapper ->
                    SwipeableNoteCard(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        noteDetailWrapper = wrapper,
                        onClick = { onClick(wrapper.note) },
                        onLongClick = { onLongClick(wrapper.note) },
                        isSwipeable = isSwipeable,
                        isSelected = selectedNotes.contains(wrapper.note),
                        onDismissed = onDismiss,
                        noteStyle = noteStyle,
                    )
                }
            }
        }
    }
}
