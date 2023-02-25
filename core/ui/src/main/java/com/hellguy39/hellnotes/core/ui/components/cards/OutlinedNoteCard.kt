package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.util.ColorParam
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedNoteCard(
    modifier: Modifier = Modifier,
    noteDetailWrapper: NoteDetailWrapper,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {

    val fraction = if (isSelected) 1f else 0f
    val borderColor by animateColorAsState(
        targetValue = lerp(
            MaterialTheme.colorScheme.outline,
            MaterialTheme.colorScheme.primary,
            FastOutSlowInEasing.transform(fraction)
        ),
        animationSpec = tween(200)//spring(stiffness = Spring.StiffnessLow)
    )

    val borderSize by animateDpAsState(
        targetValue = lerp(
            1.dp,
            2.dp,
            FastOutLinearInEasing.transform(fraction)
        ),
        animationSpec = tween(200)//spring(stiffness = Spring.StiffnessLow)
    )

    val cardBorder = BorderStroke(borderSize, borderColor)

    val colors = CardDefaults.outlinedCardColors(
        containerColor = if (noteDetailWrapper.note.colorHex == ColorParam.DefaultColor)
            Color.Transparent
        else
            Color(noteDetailWrapper.note.colorHex)
    )

    val isTitleValid = noteDetailWrapper.note.title.isNotEmpty() || noteDetailWrapper.note.title.isNotBlank()
    val isNoteValid = noteDetailWrapper.note.note.isNotEmpty() || noteDetailWrapper.note.note.isNotBlank()
    val isChipsValid = noteDetailWrapper.labels.isNotEmpty() || noteDetailWrapper.reminders.isNotEmpty()

    OutlinedCard(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        border = cardBorder
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
