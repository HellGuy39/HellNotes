package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.model.util.ColorParam
import com.hellguy39.hellnotes.core.ui.DateHelper

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.NoteCard(
    note: Note,
    isSelected: Boolean = false,
    selection: NoteSelection,
    reminds: List<Remind> = listOf(),
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
    val isChipsValid = labels.isNotEmpty() || reminds.isNotEmpty()

    OutlinedCard(
        //onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = { selection.onClick(note) },
                onLongClick = { selection.onLongClick(note) }
            )
            .animateItemPlacement(
                animationSpec = tween(
                    durationMillis = 300
                )
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

                NoteChipGroup(
                    modifier = Modifier
                        .padding(top = if (isTitleValid || isNoteValid) 6.dp else 0.dp),
                    reminders = reminds,
                    labels = labels,
                    limitElements = true,
                    maxElements = 2,
                    dateHelper = selection.dateHelper
                )

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGridItemScope.NoteCard(
    note: Note,
    isSelected: Boolean = false,
    selection: NoteSelection,
    reminds: List<Remind> = listOf(),
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
    val isChipsValid = labels.isNotEmpty() || reminds.isNotEmpty()

    OutlinedCard(
        //onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = { selection.onClick(note) },
                onLongClick = { selection.onLongClick(note) }
            )
            .animateItemPlacement(
                animationSpec = tween(
                    durationMillis = 300
                )
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

                NoteChipGroup(
                    modifier = Modifier
                        .padding(top = if (isTitleValid || isNoteValid) 6.dp else 0.dp),
                    reminders = reminds,
                    labels = labels,
                    limitElements = true,
                    maxElements = 2,
                    dateHelper = selection.dateHelper
                )

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    isSelected: Boolean = false,
    selection: NoteSelection,
    reminds: List<Remind> = listOf(),
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
    val isChipsValid = labels.isNotEmpty() || reminds.isNotEmpty()

    OutlinedCard(
        //onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = { selection.onClick(note) },
                onLongClick = { selection.onLongClick(note) }
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

                NoteChipGroup(
                    modifier = Modifier
                        .padding(top = if (isTitleValid || isNoteValid) 6.dp else 0.dp),
                    reminders = reminds,
                    labels = labels,
                    limitElements = true,
                    maxElements = 2,
                    dateHelper = selection.dateHelper
                )

            }
        }
    }
}

data class NoteSelection(
    val dateHelper: DateHelper,
    val onClick: (Note) -> Unit,
    val onLongClick: (Note) -> Unit
)