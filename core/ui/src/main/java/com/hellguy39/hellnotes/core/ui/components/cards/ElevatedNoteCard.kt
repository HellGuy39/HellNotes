package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup

@Composable
fun ElevatedNoteCard(
    modifier: Modifier = Modifier,
    noteDetailWrapper: NoteDetailWrapper,
    isSelected: Boolean = false,
) {
    val fraction = if (isSelected) 1f else 0f
    val borderColor by animateColorAsState(
        targetValue = androidx.compose.ui.graphics.lerp(
            Color.Transparent,
            MaterialTheme.colorScheme.primary,
            FastOutSlowInEasing.transform(fraction)
        ),
        animationSpec = tween(200)
    )

    val borderSize by animateDpAsState(
        targetValue = lerp(
            0.dp,
            2.dp,
            FastOutLinearInEasing.transform(fraction)
        ),
        animationSpec = tween(200)
    )

    val isTitleValid = noteDetailWrapper.note.title.isNotEmpty() || noteDetailWrapper.note.title.isNotBlank()
    val isNoteValid = noteDetailWrapper.note.note.isNotEmpty() || noteDetailWrapper.note.note.isNotBlank()
    val isChipsValid = noteDetailWrapper.labels.isNotEmpty() || noteDetailWrapper.reminders.isNotEmpty()

    ElevatedCard(
        modifier = modifier.border(
            width = borderSize,
            color = borderColor,
            shape = RoundedCornerShape(12.dp)
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp, bottom = if (isChipsValid) 8.dp else 12.dp)
        ) {
            if (isTitleValid) {
                Text(
                    text = noteDetailWrapper.note.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (isNoteValid) {
                Text(
                    text = noteDetailWrapper.note.note,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    modifier = Modifier.padding(top = if (isTitleValid) 6.dp else 0.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (isChipsValid) {
                NoteChipGroup(
                    modifier = Modifier
                        .padding(top = if (isTitleValid || isNoteValid) 6.dp else 0.dp),
                    reminders = noteDetailWrapper.reminders,
                    labels = noteDetailWrapper.labels,
                    limitElements = true,
                    maxElements = 2,
                )
            }
        }
    }
}
