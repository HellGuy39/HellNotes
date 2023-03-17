package com.hellguy39.hellnotes.core.ui.components.cards

import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hellguy39.hellnotes.core.model.CheckItem
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.util.ColorParam
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

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
    val isChecklistValid = noteDetailWrapper.note.checklist.isNotEmpty()

    OutlinedCard(
        modifier = modifier,
        colors = colors,
        border = cardBorder
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
                    modifier = Modifier,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (isChecklistValid) {
                NoteChecklistGroup(
                    checklist = noteDetailWrapper.note.checklist
                        .filter { !it.isChecked }
                        .sortedBy { it.position }
                )
            }

            if (isChipsValid) {
                NoteChipGroup(
                    modifier = Modifier,
                    reminders = noteDetailWrapper.reminders,
                    labels = noteDetailWrapper.labels,
                    limitElements = true,
                    maxElements = 2,
                )
            }
        }
    }
}

@Composable
fun NoteChecklistGroup(
    modifier: Modifier = Modifier,
    checklist: List<CheckItem> = listOf()
) {
    val uncheckedCount = if (checklist.size > 3) checklist.size - 3 else 0
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(if (checklist.size > 3) 3 else checklist.size) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = HellNotesIcons.CheckboxUnchecked),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = checklist[index].text,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        if (uncheckedCount >= 1) {
            Text(
                text = "+$uncheckedCount unchecked item(s)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
