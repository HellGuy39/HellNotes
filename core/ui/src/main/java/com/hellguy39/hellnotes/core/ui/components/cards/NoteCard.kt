package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.Reminder
import com.hellguy39.hellnotes.core.model.util.ColorParam
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    isSelected: Boolean = false,
    reminders: List<Reminder> = listOf(),
    labels: List<Label> = listOf(),
) {

    val cardBorder = if (isSelected)
        BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    else
        CardDefaults.outlinedCardBorder()

    val colors = CardDefaults.outlinedCardColors(
        containerColor = if (note.colorHex == ColorParam.DefaultColor)
            Color.Transparent
        else
            Color(note.colorHex)
    )

    val isTitleValid = note.title.isNotEmpty() || note.title.isNotBlank()
    val isNoteValid = note.note.isNotEmpty() || note.note.isNotBlank()
    val isChipsValid = labels.isNotEmpty() || reminders.isNotEmpty()

    OutlinedCard(
        //onClick = { onClick() },
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
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (isNoteValid) {
                Text(
                    text = note.note,
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
                    reminders = reminders,
                    labels = labels,
                    limitElements = true,
                    maxElements = 2,
                )
            }
        }
    }
}

data class NoteSelection(
    val onClick: (Note) -> Unit,
    val onLongClick: (Note) -> Unit,
)