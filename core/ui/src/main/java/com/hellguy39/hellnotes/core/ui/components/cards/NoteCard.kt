package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.animation.core.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    noteDetailWrapper: NoteDetailWrapper,
    isSelected: Boolean = false,
    noteStyle: NoteStyle
) {
    when(noteStyle) {
        NoteStyle.Outlined -> {
            OutlinedNoteCard(
                modifier = modifier,
                noteDetailWrapper = noteDetailWrapper,
                isSelected = isSelected
            )
        }
        NoteStyle.Elevated -> {
            ElevatedNoteCard(
                modifier = modifier,
                noteDetailWrapper = noteDetailWrapper,
                isSelected = isSelected
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
data class NoteSelection(
    val noteStyle: NoteStyle,
    val isSwipeable: Boolean,
    val onClick: (Note) -> Unit,
    val onLongClick: (Note) -> Unit,
    val onDismiss: (DismissDirection, Note) -> Boolean
)