package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Reminder
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_detail.NoteDetailUiState

@Composable
fun NoteDetailContent(
    innerPadding: PaddingValues,
    uiState: NoteDetailUiState,
    selection: NoteDetailContentSelection,
    focusRequester: FocusRequester
) {
    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = uiState.note.title,
                    isSingleLine = false,
                    onValueChange = { newText -> selection.onTitleTextChanged(newText) },
                    hint = stringResource(id = HellNotesStrings.Hint.Title),
                    textStyle = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth()
                )
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = uiState.note.note,
                    isSingleLine = false,
                    onValueChange = { newText -> selection.onNoteTextChanged(newText) },
                    hint = stringResource(id = HellNotesStrings.Hint.Note),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
        item {
            NoteChipGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                reminders = uiState.noteReminders,
                labels = uiState.noteLabels,
                onRemindClick = { remind ->
                    selection.onReminderClick(remind)
                },
                onLabelClick = { label ->
                    selection.onLabelClick(label)
                },
            )
        }
    }
}

data class NoteDetailContentSelection(
    val onTitleTextChanged: (text: String) -> Unit,
    val onNoteTextChanged: (text: String) -> Unit,
    val onReminderClick: (reminder: Reminder) -> Unit,
    val onLabelClick: (label: Label) -> Unit
)