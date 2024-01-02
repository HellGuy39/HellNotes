package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.SwipeableNoteCard
import com.hellguy39.hellnotes.core.ui.isSingleList
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun NoteColumnList(
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
    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding,
    ) {
        item {
            listHeader()
        }
        categories.forEach { category ->
            if (category.notes.isNotEmpty()) {
                if (category.title.isNotEmpty() && !categories.isSingleList()) {
                    item {
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
                    key = { it.note.id ?: 0 },
                ) { wrapper ->
                    SwipeableNoteCard(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(Spaces.extraSmall)
                                .animateItemPlacement(),
                        noteDetailWrapper = wrapper,
                        isSwipeable = isSwipeable,
                        isSelected = selectedNotes.contains(wrapper.note),
                        onDismissed = onDismiss,
                        onClick = { onClick(wrapper.note) },
                        onLongClick = { onLongClick(wrapper.note) },
                        noteStyle = noteStyle,
                    )
                }
            }
        }
    }
}
