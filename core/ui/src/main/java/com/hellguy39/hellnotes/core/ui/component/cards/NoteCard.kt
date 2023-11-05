package com.hellguy39.hellnotes.core.ui.component.cards

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.value.elevation

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    noteWrapper: NoteWrapper,
    openedNoteId: Long,
    isSelected: Boolean,
    noteStyle: NoteStyle
) {

    val isOpen = noteWrapper.note.id != Note.EMPTY_ID && noteWrapper.note.id == openedNoteId
    val fraction = if (isOpen) 1f else 0f

//    val cardBorder by animateDpAsState(
//        targetValue = lerp(
//            0.dp,
//            2.dp,
//            FastOutLinearInEasing.transform(fraction)
//        ),
//        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
//        label = "label"
//    )

    OutlinedNoteCard(
        modifier = modifier,
        noteWrapper = noteWrapper,
        isSelected = isOpen
    )

//    Surface(
//        modifier = modifier,
//        color = MaterialTheme.colorScheme.surfaceColorAtElevation(cardElevation),
//        shape = MaterialTheme.shapes.medium
//    ) {
//        NoteCardContent(noteWrapper = noteWrapper)
//    }

//    when {
//        noteWrapper.note.id != Note.EMPTY_ID && noteWrapper.note.id == openedNoteId -> {
//            ElevatedNoteCard(
//                modifier = modifier,
//                noteWrapper = noteWrapper,
//                isSelected = isSelected
//            )
//        }
//        else -> {
//            OutlinedNoteCard(
//                modifier = modifier,
//                noteWrapper = noteWrapper,
//                isSelected = isSelected
//            )
//        }
//    }
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