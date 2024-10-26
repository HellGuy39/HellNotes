/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.ui.components.items

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.components.cards.SwipeableNoteCard
import com.hellguy39.hellnotes.core.ui.values.Duration
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
fun LazyListScope.noteItems(
    state: LazyListState,
    notes: SnapshotStateList<Selectable<NoteWrapper>>,
    isSwipeable: Boolean,
    onClick: (noteId: Long?) -> Unit,
    onLongClick: (noteId: Long?) -> Unit,
    onDismiss: (SwipeToDismissBoxValue, noteId: Long?) -> Boolean,
    noteStyle: NoteStyle,
) {
    itemsIndexed(
        items = notes,
        key = { _, wrapper -> wrapper.value.note.id ?: 0 },
        contentType = { _, selectable -> selectable },
    ) { index, wrapper ->
        val animatableAlpha = remember { Animatable(0f) }
        val isVisible =
            remember {
                derivedStateOf {
                    state.firstVisibleItemIndex <= index
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

        val noteId = wrapper.value.note.id

        SwipeableNoteCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Spaces.extraSmall)
                    .alpha(animatableAlpha.value)
                    .animateItemPlacement(),
            noteWrapper = wrapper.value,
            isSwipeable = isSwipeable,
            isSelected = wrapper.selected,
            onDismissed = { direction -> onDismiss(direction, noteId) },
            onClick = { onClick(noteId) },
            onLongClick = { onLongClick(noteId) },
            noteStyle = noteStyle,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
fun LazyStaggeredGridScope.noteItems(
    state: LazyStaggeredGridState,
    notes: SnapshotStateList<Selectable<NoteWrapper>>,
    isSwipeable: Boolean,
    onClick: (noteId: Long?) -> Unit,
    onLongClick: (noteId: Long?) -> Unit,
    onDismiss: (SwipeToDismissBoxValue, noteId: Long?) -> Boolean,
    noteStyle: NoteStyle,
) {
    itemsIndexed(
        items = notes,
        key = { _, wrapper -> wrapper.value.note.id ?: 0 },
        contentType = { _, selectable -> selectable },
    ) { index, wrapper ->
        val animatableAlpha = remember { Animatable(0f) }
        val isVisible =
            remember {
                derivedStateOf {
                    state.firstVisibleItemIndex <= index
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

        val noteId = wrapper.value.note.id

        SwipeableNoteCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Spaces.extraSmall)
                    .alpha(animatableAlpha.value)
                    .animateItemPlacement(),
            noteWrapper = wrapper.value,
            isSwipeable = isSwipeable,
            isSelected = wrapper.selected,
            onDismissed = { direction -> onDismiss(direction, noteId) },
            onClick = { onClick(noteId) },
            onLongClick = { onLongClick(noteId) },
            noteStyle = noteStyle,
        )
    }
}
