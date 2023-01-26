package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_detail.NoteDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailContent(
    innerPadding: PaddingValues,
    uiState: NoteDetailUiState,
    selection: NoteDetailContentSelection,
    dateHelper: DateHelper,
    focusRequester: FocusRequester
) {
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            OutlinedTextField(
                value = uiState.note.title,
                onValueChange = { newText -> selection.onTitleTextChanged(newText) },
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
                value = uiState.note.note,
                onValueChange = { newText -> selection.onNoteTextChanged(newText) },
                placeholder = {
                    Text(
                        text = stringResource(id = HellNotesStrings.Hint.Note),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            NoteChipGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                reminders = uiState.noteReminders,
                labels = uiState.noteLabels,
                onRemindClick = { remind ->
                    selection.onReminderClick(remind)
                },
                onLabelClick = { label ->
                    selection.onLabelClick(label)
                },
                dateHelper = dateHelper
            )
        }
        item {
            val dateVisibility = uiState.note.lastEditDate != 0L

            AnimatedVisibility(
                visible = dateVisibility,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Text(
                    text = stringResource(
                        id = HellNotesStrings.Text.Edited,
                        formatArgs = arrayOf(
                            dateHelper.formatBest(uiState.note.lastEditDate)
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

data class NoteDetailContentSelection(
    val onTitleTextChanged: (text: String) -> Unit,
    val onNoteTextChanged: (text: String) -> Unit,
    val onReminderClick: (remind: Remind) -> Unit,
    val onLabelClick: (label: Label) -> Unit
)