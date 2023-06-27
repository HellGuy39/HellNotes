package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableNoteCard(
    modifier: Modifier = Modifier,
    noteStyle: NoteStyle,
    noteWrapper: NoteWrapper,
    isSelected: Boolean = false,
    isSwipeable: Boolean = true,
    onDismissed: (DismissDirection, NoteWrapper) -> Boolean
) {
    if (isSwipeable) {
        val dismissState = rememberDismissState(
            confirmValueChange = { dismissValue ->
                when (dismissValue) {
                    DismissValue.DismissedToEnd -> {
                        onDismissed(DismissDirection.StartToEnd, noteWrapper)
                    }
                    DismissValue.DismissedToStart -> {
                        onDismissed(DismissDirection.EndToStart, noteWrapper)
                    }
                    else -> { false }
                }
            }
        )
        SwipeToDismiss(
            modifier = Modifier,
            state = dismissState,
            directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
            background = {},
            dismissContent = {
                val visibility = if (dismissState.progress == 1f) 1f else 1f - dismissState.progress

                NoteCard(
                    modifier = modifier.alpha(visibility),
                    noteWrapper = noteWrapper,
                    isSelected = isSelected,
                    noteStyle = noteStyle
                )
            }
        )
    } else {
        NoteCard(
            modifier = modifier,
            noteWrapper = noteWrapper,
            isSelected = isSelected,
            noteStyle = noteStyle
        )
    }

}