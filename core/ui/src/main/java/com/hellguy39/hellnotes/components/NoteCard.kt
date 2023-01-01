package com.hellguy39.hellnotes.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.ColorParam

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit
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

    OutlinedCard(
        //onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    onLongClick()
                }
            ),
        colors = colors,
        border = cardBorder
    ) {
        Column(
            modifier = Modifier
                .padding(all = 12.dp)
        ) {
            val isTitleValid = note.title.isNotEmpty() || note.title.isNotBlank()
            val isNoteValid = note.note.isNotEmpty() || note.note.isNotBlank()

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
        }
    }
}