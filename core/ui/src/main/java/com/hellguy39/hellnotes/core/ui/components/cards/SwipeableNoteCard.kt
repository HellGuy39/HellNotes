package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableNoteCard(
    modifier: Modifier = Modifier,
    noteDetailWrapper: NoteDetailWrapper,
    isSelected: Boolean = false,
    onDismissed: (DismissDirection, Note) -> Boolean
) {
    val dismissState = rememberDismissState(
        confirmValueChange = {
            when (it) {
                DismissValue.DismissedToEnd -> {
                    onDismissed(DismissDirection.StartToEnd, noteDetailWrapper.note)
                }
                DismissValue.DismissedToStart -> {
                    onDismissed(DismissDirection.EndToStart, noteDetailWrapper.note)
                }
                else -> { false }
            }
        },
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
                noteDetailWrapper = noteDetailWrapper,
                isSelected = isSelected
            )
        }
    )

}