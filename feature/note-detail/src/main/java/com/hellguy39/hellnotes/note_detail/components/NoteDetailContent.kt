package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.common.date.DateHelper
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.note_detail.events.LabelDialogEvents
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailContent(
    innerPadding: PaddingValues,
    note: Note,
    reminds: List<Remind>,
    labels: List<Label>,
    onTitleTextChanged: (text: String) -> Unit,
    onNoteTextChanged: (text: String) -> Unit,
    labelDialogEvents: LabelDialogEvents
) {
    val dateVisibility = note.lastEditDate != 0L

    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            OutlinedTextField(
                value = note.title,
                onValueChange = { newText -> onTitleTextChanged(newText) },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = HellNotesStrings.Hint.Title),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge,

            )
        }
        item {
            OutlinedTextField(
                value = note.note,
                onValueChange = { newText -> onNoteTextChanged(newText) },
                placeholder = {
                    Text(
                        text = stringResource(id = HellNotesStrings.Hint.Note),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodyLarge
            )
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                        onClick = { labelDialogEvents.show() },
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
        item {
            AnimatedVisibility(
                visible = dateVisibility,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Text(
                    text = stringResource(
                        id = HellNotesStrings.Text.Edited,
                        formatArgs = arrayOf(
                            DateHelper(LocalContext.current)
                                .formatAsLastEditDate(note.lastEditDate)
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}