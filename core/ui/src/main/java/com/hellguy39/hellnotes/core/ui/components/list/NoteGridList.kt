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
package com.hellguy39.hellnotes.core.ui.components.list

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.components.items.noteItems
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.wrapper.UiVolume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteGridList(
    modifier: Modifier,
    innerPadding: PaddingValues,
    noteStyle: NoteStyle,
    isSwipeable: Boolean,
    onClick: (noteId: Long?) -> Unit,
    onLongClick: (noteId: Long?) -> Unit,
    onDismiss: (SwipeToDismissBoxValue, noteId: Long?) -> Boolean,
    volume: UiVolume<Selectable<NoteWrapper>>,
    listHeader: @Composable () -> Unit,
) {
    val state = rememberLazyStaggeredGridState()

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
        volume.partitions.forEachIndexed { index, partition ->
            if (!partition.isEmpty) {
                if (partition.name !is UiText.Empty && !volume.isSinglePartition) {
                    item(
                        key = Int.MIN_VALUE - index,
                        span = StaggeredGridItemSpan.FullLine,
                    ) {
                        Text(
                            text = partition.name.asString(),
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
                noteItems(
                    state = state,
                    notes = partition.elements,
                    isSwipeable = isSwipeable,
                    onClick = onClick,
                    onLongClick = onLongClick,
                    onDismiss = onDismiss,
                    noteStyle = noteStyle,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteGridList(
    modifier: Modifier,
    innerPadding: PaddingValues,
    noteStyle: NoteStyle,
    isSwipeable: Boolean,
    onClick: (noteId: Long?) -> Unit,
    onLongClick: (noteId: Long?) -> Unit,
    onDismiss: (SwipeToDismissBoxValue, noteId: Long?) -> Boolean,
    notes: SnapshotStateList<Selectable<NoteWrapper>>,
    listHeader: @Composable () -> Unit,
) {
    val state = rememberLazyStaggeredGridState()

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
        noteItems(
            state = state,
            notes = notes,
            isSwipeable = isSwipeable,
            onClick = onClick,
            onLongClick = onLongClick,
            onDismiss = onDismiss,
            noteStyle = noteStyle,
        )
    }
}
