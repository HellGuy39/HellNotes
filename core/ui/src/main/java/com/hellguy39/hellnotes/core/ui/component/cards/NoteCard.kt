package com.hellguy39.hellnotes.core.ui.component.cards

import androidx.compose.animation.core.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    noteWrapper: NoteWrapper,
    openedNoteId: Long,
    isSelected: Boolean,
    noteStyle: NoteStyle
) {
    when {
        noteWrapper.note.id != Note.EMPTY_ID && noteWrapper.note.id == openedNoteId -> {
            ElevatedNoteCard(
                modifier = modifier,
                noteWrapper = noteWrapper,
                isSelected = isSelected
            )
        }
        else -> {
            OutlinedNoteCard(
                modifier = modifier,
                noteWrapper = noteWrapper,
                isSelected = isSelected
            )
        }
    }
//    when(noteStyle) {
//        NoteStyle.Outlined -> {
//            OutlinedNoteCard(
//                modifier = modifier,
//                noteWrapper = noteWrapper,
//                isSelected = isSelected
//            )
//        }
//        NoteStyle.Elevated -> {
//            ElevatedNoteCard(
//                modifier = modifier,
//                noteWrapper = noteWrapper,
//                isSelected = isSelected
//            )
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
data class NoteSelection(
    val noteStyle: NoteStyle = NoteStyle.Outlined,
    val isSwipeable: Boolean = false,
    val onClick: (NoteWrapper) -> Unit = {},
    val onLongClick: (NoteWrapper) -> Unit = {},
    val onDismiss: (DismissDirection, NoteWrapper) -> Boolean = { _, _ -> false }
)