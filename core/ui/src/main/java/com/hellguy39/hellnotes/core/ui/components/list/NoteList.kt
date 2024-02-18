package com.hellguy39.hellnotes.core.ui.components.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.wrapper.Selectable
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.core.ui.wrapper.UiVolume

@Composable
private fun rememberListModifier(): Modifier {
    return remember {
        Modifier
            .fillMaxSize()
            .padding(horizontal = Spaces.extraSmall, vertical = Spaces.extraSmall)
            .testTag("item_list")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteStyle: NoteStyle,
    isSwipeable: Boolean = false,
    onClick: (noteId: Long?) -> Unit,
    onLongClick: (noteId: Long?) -> Unit,
    onDismiss: (SwipeToDismissBoxValue, noteId: Long?) -> Boolean = remember { { _, _ -> false } },
    volume: UiVolume<Selectable<NoteWrapper>>,
    listStyle: ListStyle = ListStyle.Column,
    listHeader: @Composable () -> Unit = {},
) {
    val listModifier = rememberListModifier()

    when (listStyle) {
        ListStyle.Column -> {
            NoteColumnList(
                modifier = listModifier,
                noteStyle = noteStyle,
                isSwipeable = isSwipeable,
                onClick = onClick,
                onLongClick = onLongClick,
                onDismiss = onDismiss,
                innerPadding = innerPadding,
                volume = volume,
                listHeader = listHeader,
            )
        }
        ListStyle.Grid -> {
            NoteGridList(
                modifier = listModifier,
                innerPadding = innerPadding,
                isSwipeable = isSwipeable,
                onClick = onClick,
                onLongClick = onLongClick,
                onDismiss = onDismiss,
                volume = volume,
                noteStyle = noteStyle,
                listHeader = listHeader,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    noteStyle: NoteStyle,
    isSwipeable: Boolean = false,
    onClick: (noteId: Long?) -> Unit,
    onLongClick: (noteId: Long?) -> Unit,
    onDismiss: (SwipeToDismissBoxValue, noteId: Long?) -> Boolean = remember { { _, _ -> false } },
    notes: SnapshotStateList<Selectable<NoteWrapper>> = mutableStateListOf(),
    listStyle: ListStyle = ListStyle.Column,
    listHeader: @Composable () -> Unit = {},
) {
    val listModifier = rememberListModifier()

    when (listStyle) {
        ListStyle.Column -> {
            NoteColumnList(
                modifier = listModifier,
                noteStyle = noteStyle,
                isSwipeable = isSwipeable,
                onClick = onClick,
                onLongClick = onLongClick,
                onDismiss = onDismiss,
                innerPadding = innerPadding,
                listHeader = listHeader,
                notes = notes,
            )
        }
        ListStyle.Grid -> {
            NoteGridList(
                modifier = listModifier,
                innerPadding = innerPadding,
                isSwipeable = isSwipeable,
                onClick = onClick,
                onLongClick = onLongClick,
                onDismiss = onDismiss,
                notes = notes,
                noteStyle = noteStyle,
                listHeader = listHeader,
            )
        }
    }
}
