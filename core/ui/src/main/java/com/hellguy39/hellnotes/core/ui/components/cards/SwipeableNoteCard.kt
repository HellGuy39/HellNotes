package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableNoteCard(
    modifier: Modifier = Modifier,
    noteStyle: NoteStyle,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    noteWrapper: NoteWrapper,
    isSelected: Boolean,
    isSwipeable: Boolean,
    onDismissed: (DismissDirection, Note) -> Boolean,
) {
    val dismissState =
        rememberDismissState(
            confirmValueChange = { dismissValue ->
                when (dismissValue) {
                    DismissValue.DismissedToEnd -> {
                        onDismissed(DismissDirection.StartToEnd, noteWrapper.note)
                    }
                    DismissValue.DismissedToStart -> {
                        onDismissed(DismissDirection.EndToStart, noteWrapper.note)
                    }
                    else -> false
                }
            },
        )

    val swipeDirections by remember {
        derivedStateOf {
            if (isSwipeable) {
                setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart)
            } else {
                setOf()
            }
        }
    }

    val visibility by remember {
        derivedStateOf {
            if (dismissState.progress == 1f) 1f else 1f - dismissState.progress
        }
    }

    SwipeToDismiss(
        modifier = Modifier,
        state = dismissState,
        directions = swipeDirections,
        background = { /* no-op */ },
        dismissContent = {
            NoteCard(
                modifier = modifier.alpha(visibility),
                onClick = onClick,
                onLongClick = onLongClick,
                noteWrapper = noteWrapper,
                isSelected = isSelected,
                noteStyle = noteStyle,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableNoteCard(
    modifier: Modifier = Modifier,
    noteStyle: NoteStyle,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    noteWrapper: NoteWrapper,
    isSelected: Boolean,
    isSwipeable: Boolean,
    onDismissed: (DismissDirection) -> Boolean,
) {
    val dismissState =
        rememberDismissState(
            confirmValueChange = { dismissValue ->
                when (dismissValue) {
                    DismissValue.DismissedToEnd -> {
                        onDismissed(DismissDirection.StartToEnd)
                    }
                    DismissValue.DismissedToStart -> {
                        onDismissed(DismissDirection.EndToStart)
                    }
                    else -> false
                }
            },
        )

    val swipeDirections by remember {
        derivedStateOf {
            if (isSwipeable) {
                setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart)
            } else {
                setOf()
            }
        }
    }

    val visibility by remember {
        derivedStateOf {
            if (dismissState.progress == 1f) 1f else 1f - dismissState.progress
        }
    }

    SwipeToDismiss(
        modifier = Modifier,
        state = dismissState,
        directions = swipeDirections,
        background = { /* no-op */ },
        dismissContent = {
            NoteCard(
                modifier = modifier.alpha(visibility),
                onClick = onClick,
                onLongClick = onLongClick,
                noteWrapper = noteWrapper,
                isSelected = isSelected,
                noteStyle = noteStyle,
            )
        },
    )
}
