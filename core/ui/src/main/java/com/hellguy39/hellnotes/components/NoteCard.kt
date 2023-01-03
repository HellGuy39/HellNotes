package com.hellguy39.hellnotes.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.model.util.ColorParam
import com.hellguy39.hellnotes.ui.HellNotesIcons

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    note: Note,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    reminds: List<Remind> = listOf(),
    labels: List<Label> = listOf()
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
    val isChipsValid = labels.isNotEmpty() || reminds.isNotEmpty()

    OutlinedCard(
        //onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            ),
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
                LazyRow(
                    modifier = Modifier.padding(top = if (isTitleValid || isNoteValid) 6.dp else 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val chipsCount = reminds.size + labels.size
                    
                    items(reminds) { remind ->
                        FilterChip(
                            selected = true,
                            onClick = { /* Do something! */ },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Alarm),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    text = remind.triggerDate.toString(),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                        )
                    }
                    items(labels) { label ->
                        FilterChip(
                            selected = true,
                            onClick = { /* Do something! */ },
                            label = {
                                Text(
                                    text = label.name,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}