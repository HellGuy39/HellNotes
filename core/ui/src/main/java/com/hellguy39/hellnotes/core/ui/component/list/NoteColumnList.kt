package com.hellguy39.hellnotes.core.ui.component.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.component.cards.SwipeableNoteCard
import com.hellguy39.hellnotes.core.ui.util.ContentGroup
import com.hellguy39.hellnotes.core.ui.util.isSingleGroup

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun NoteColumnList(
    modifier: Modifier,
    innerPadding: PaddingValues,
    noteSelection: NoteSelection,
    openedNoteId: Long,
    showNameIfSingleGroup: Boolean,
    contentGroups: List<ContentGroup<NoteWrapper>>,
    selectedNoteWrappers: List<NoteWrapper>,
    lazyListState: LazyListState,
) {
    val haptic = LocalHapticFeedback.current
    val isSingleGroup = contentGroups.isSingleGroup()

    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding,
        state = lazyListState
    ) {
        for (contentGroup in contentGroups) {

            if (contentGroup.content.isEmpty()) {
                continue
            }

            if (contentGroup.name.isNotEmpty() && (showNameIfSingleGroup || !isSingleGroup)) {
                item {
                    Text(
                        text = contentGroup.name,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            items(
                items = contentGroup.content,
                key = { item: NoteWrapper -> item.note.id ?: 0 },
            ) { noteWrapper ->
                SwipeableNoteCard(
                    modifier = Modifier.fillMaxWidth()
                        .padding(4.dp)
                        .combinedClickable(
                            onClick = { noteSelection.onClick(noteWrapper) },
                            onLongClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                noteSelection.onLongClick(noteWrapper)
                            }
                        )
                        .animateItemPlacement(),
                    noteWrapper = noteWrapper,
                    openedNoteId = openedNoteId,
                    isSwipeable = noteSelection.isSwipeable,
                    isSelected = selectedNoteWrappers.contains(noteWrapper),
                    onDismissed = noteSelection.onDismiss,
                    noteStyle = noteSelection.noteStyle
                )
            }
        }
    }
}