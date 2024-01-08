package com.hellguy39.hellnotes.core.ui.components.list

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.SwipeableNoteCard
import com.hellguy39.hellnotes.core.ui.isSingleList
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteGridList(
    modifier: Modifier,
    innerPadding: PaddingValues,
    noteStyle: NoteStyle,
    isSwipeable: Boolean,
    onClick: (Note) -> Unit,
    onLongClick: (Note) -> Unit,
    onDismiss: (DismissDirection, Note) -> Boolean,
    categories: SnapshotStateList<NoteCategory>,
    selectedNotes: SnapshotStateList<Note>,
    listHeader: @Composable () -> Unit,
) {
    val scrollState = rememberLazyStaggeredGridState()

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
                if (category.title !is UiText.Empty && !categories.isSingleList()) {
                    item(
                        span = StaggeredGridItemSpan.FullLine,
                    ) {
                        Text(
                            text = category.title.asString(),
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
                itemsIndexed(
                    items = category.notes,
                    key = { _, note -> note.note.id ?: 0 },
                ) { index, wrapper ->
                    val animatableAlpha = remember { Animatable(0f) }
                    val isVisible =
                        remember {
                            derivedStateOf {
                                scrollState.firstVisibleItemIndex <= index
                            }
                        }

                    LaunchedEffect(isVisible.value) {
                        if (isVisible.value) {
                            animatableAlpha.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(durationMillis = Duration.SLOW),
                            )
                        }
                    }

                    val isSelected by remember {
                        derivedStateOf {
                            selectedNotes.contains(wrapper.note)
                        }
                    }
                    SwipeableNoteCard(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .alpha(animatableAlpha.value)
                                .padding(4.dp),
                        noteDetailWrapper = wrapper,
                        onClick = { onClick(wrapper.note) },
                        onLongClick = { onLongClick(wrapper.note) },
                        isSwipeable = isSwipeable,
                        isSelected = isSelected,
                        onDismissed = onDismiss,
                        noteStyle = noteStyle,
                    )
                }
            }
        }
    }
}
