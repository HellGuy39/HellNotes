package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
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
import com.hellguy39.hellnotes.core.ui.components.NoteChecklistGroup
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup

@Composable
fun OutlinedNoteCard(
    modifier: Modifier = Modifier,
    noteDetailWrapper: NoteDetailWrapper,
    isSelected: Boolean = false,
) {

    val fraction = if (isSelected) 1f else 0f
    val borderColor by animateColorAsState(
        targetValue = lerp(
            MaterialTheme.colorScheme.outline,
            MaterialTheme.colorScheme.primary,
            FastOutSlowInEasing.transform(fraction)
        ),
        animationSpec = tween(200)
    )

    val borderSize by animateDpAsState(
        targetValue = lerp(
            1.dp,
            2.dp,
            FastOutLinearInEasing.transform(fraction)
        ),
        animationSpec = tween(200)
    )

    val cardBorder = BorderStroke(borderSize, borderColor)

    val colors = CardDefaults.outlinedCardColors(
        containerColor = if (noteDetailWrapper.note.colorHex == ColorParam.DefaultColor)
            Color.Transparent
        else
            Color(noteDetailWrapper.note.colorHex)
    )

    OutlinedCard(
        modifier = modifier,
        colors = colors,
        border = cardBorder
    ) {
        NoteCardContent(noteDetailWrapper = noteDetailWrapper)
    }
}