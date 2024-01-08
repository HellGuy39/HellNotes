package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.SwipeableNoteCard
import com.hellguy39.hellnotes.core.ui.isSingleList
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Duration
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun NoteColumnList(
    modifier: Modifier = Modifier,
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
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding,
        state = scrollState,
    ) {
        item {
            listHeader()
        }
        categories.forEach { category ->
            if (category.notes.isNotEmpty()) {
                if (category.title !is UiText.Empty && !categories.isSingleList()) {
                    item(key = -1) {
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

                    SwipeableNoteCard(
                        modifier =
                            Modifier.fillMaxWidth()
                                .padding(Spaces.extraSmall)
                                .alpha(animatableAlpha.value)
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
