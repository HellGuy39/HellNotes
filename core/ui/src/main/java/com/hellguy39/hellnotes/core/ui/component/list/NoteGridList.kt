package com.hellguy39.hellnotes.core.ui.component.list

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.ui.component.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.component.cards.SwipeableNoteCard
import com.hellguy39.hellnotes.core.ui.util.ContentGroup
import com.hellguy39.hellnotes.core.ui.util.isSingleGroup

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun NoteGridList(
    modifier: Modifier,
    innerPadding: PaddingValues,
    noteSelection: NoteSelection,
    showNameIfSingleGroup: Boolean,
    contentGroups: List<ContentGroup<NoteWrapper>>,
    selectedNoteWrappers: List<NoteWrapper>,
    lazyListState: LazyStaggeredGridState
) {
    val haptic = LocalHapticFeedback.current

    val isSingleGroup = contentGroups.isSingleGroup()

    // todo: consider screen size
    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Adaptive(minSize = 192.dp)
    } else StaggeredGridCells.Fixed(2)

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        contentPadding = innerPadding,
        columns = cellConfiguration,
        state = lazyListState
    ) {
        for (contentGroup in contentGroups) {

            if (contentGroup.content.isEmpty()) {
                continue
            }

            if (contentGroup.name.isNotEmpty() && (showNameIfSingleGroup || !isSingleGroup)) {
                item(span = StaggeredGridItemSpan.FullLine) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .combinedClickable(
                            onClick = { noteSelection.onClick(noteWrapper) },
                            onLongClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                noteSelection.onLongClick(noteWrapper)
                            }
                        ),
                    noteWrapper = noteWrapper,
                    isSwipeable = noteSelection.isSwipeable,
                    isSelected = selectedNoteWrappers.contains(noteWrapper),
                    onDismissed = noteSelection.onDismiss,
                    noteStyle = noteSelection.noteStyle,
                )
            }
        }
    }
}