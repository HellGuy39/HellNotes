package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteCard
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.isSingleList

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NoteColumnList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteSelection: NoteSelection,
    categories: List<NoteCategory>,
    selectedNotes: List<Note> = listOf(),
    listHeader: @Composable () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxSize()
            .testTag("item_list"),
        contentPadding = innerPadding
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
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                items(
                    items = category.notes,
                    key = { it.note.id ?: 0 },
                ) { wrapper ->
                    NoteCard(
                        modifier = Modifier.fillMaxWidth()
                            .padding(4.dp)
                            .combinedClickable(
                                onClick = { noteSelection.onClick(wrapper.note) },
                                onLongClick = { noteSelection.onLongClick(wrapper.note) }
                            )
                            .animateItemPlacement(
                                animationSpec = tween(durationMillis = 300)
                            ),
                        note = wrapper.note,
                        isSelected = selectedNotes.contains(wrapper.note),
                        labels = wrapper.labels,
                        reminders = wrapper.reminders
                    )
                }
            }
        }
    }
}