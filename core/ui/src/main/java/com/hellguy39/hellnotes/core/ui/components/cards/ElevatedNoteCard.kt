package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.ui.values.Elevation

@Composable
fun ElevatedNoteCard(
    modifier: Modifier = Modifier,
    noteDetailWrapper: NoteDetailWrapper,
    isSelected: Boolean = false,
) {
    val fraction = if (isSelected) 1f else 0f
    val borderColor by animateColorAsState(
        targetValue =
            androidx.compose.ui.graphics.lerp(
                Color.Transparent,
                MaterialTheme.colorScheme.primary,
                FastOutSlowInEasing.transform(fraction),
            ),
        animationSpec = tween(200),
        label = "border_color",
    )

    val borderSize by animateDpAsState(
        targetValue =
            lerp(
                0.dp,
                2.dp,
                FastOutLinearInEasing.transform(fraction),
            ),
        animationSpec = tween(200),
        label = "border_size",
    )

    ElevatedCard(
        modifier =
            modifier.border(
                width = borderSize,
                color = borderColor,
                shape = RoundedCornerShape(12.dp),
            ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = Elevation.Level2),
    ) {
        NoteCardContent(noteDetailWrapper = noteDetailWrapper)
    }
}
