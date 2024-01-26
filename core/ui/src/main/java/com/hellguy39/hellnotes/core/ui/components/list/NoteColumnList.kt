package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.components.items.noteItems
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.wrapper.PartitionElementPositionInfo
import com.hellguy39.hellnotes.core.ui.wrapper.UiVolume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteColumnList(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    noteStyle: NoteStyle,
    isSwipeable: Boolean,
    onClick: (position: PartitionElementPositionInfo) -> Unit,
    onLongClick: (position: PartitionElementPositionInfo) -> Unit,
    onDismiss: (DismissDirection, position: PartitionElementPositionInfo) -> Boolean,
    volume: UiVolume<Selectable<NoteDetailWrapper>>,
    listHeader: @Composable () -> Unit,
) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding,
        state = state,
    ) {
        item {
            listHeader()
        }
        volume.partitions.forEachIndexed { index, partition ->
            if (!partition.isEmpty) {
                if (partition.name !is UiText.Empty && !volume.isSinglePartition) {
                    item(key = Int.MIN_VALUE - index) {
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
                    partitionIndex = index,
                    noteStyle = noteStyle,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteColumnList(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    noteStyle: NoteStyle,
    isSwipeable: Boolean,
    onClick: (index: Int) -> Unit,
    onLongClick: (index: Int) -> Unit,
    onDismiss: (DismissDirection, index: Int) -> Boolean,
    notes: SnapshotStateList<Selectable<NoteDetailWrapper>>,
    listHeader: @Composable () -> Unit,
) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding,
        state = state,
    ) {
        item {
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
