package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.CheckItem
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Reminder
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.components.items.SelectionIconItem
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_detail.NoteDetailUiState

@Composable
fun NoteDetailContent(
    innerPadding: PaddingValues,
    uiState: NoteDetailUiState,
    selection: NoteDetailContentSelection,
    checklistSelection: NoteDetailChecklistSelection,
    focusRequester: FocusRequester,
    lazyListState: LazyListState
) {
    LazyColumn(
        state = lazyListState,
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
                        .height(16.dp)
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
            if (uiState.note.checklist.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        items(items = uiState.note.checklist) { item ->
            CheckListItem(
                modifier = Modifier
                    .padding(4.dp),
                item = item,
                checklistSelection = checklistSelection
            )
        }
        item {
            if (uiState.note.checklist.isNotEmpty()) {
                SelectionIconItem(
                    heroIcon = painterResource(id = HellNotesIcons.Add),
                    title = "Add new item",
                    onClick = checklistSelection.onNewItem
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

@Composable
fun CheckListItem(
    modifier: Modifier = Modifier,
    item: CheckItem,
    checklistSelection: NoteDetailChecklistSelection
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    var checked by remember { mutableStateOf(item.isChecked) }
    var text by remember { mutableStateOf(item.text) }

    fun onCheckedChange(isChecked: Boolean) {
        checked = isChecked
        checklistSelection.onCheckedChange(item, isChecked)
    }

    fun onTextChange(newText: String) {
        text = newText
        checklistSelection.onChangeText(item, newText)
    }

    CustomDivider(color = MaterialTheme.colorScheme.outline)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = HellNotesIcons.DragHandle),
                contentDescription = null
            )
        }

        Checkbox(
            modifier = Modifier,
            checked = checked,
            onCheckedChange = { isChecked -> onCheckedChange(isChecked) }
        )

        CustomTextField(
            value = text,
            hint = "Item",
            onValueChange = { newText -> onTextChange(newText) },
            modifier = Modifier.weight(1f)
                .onFocusChanged { state -> isFocused = state.isFocused }
                .alpha(if (checked) UiDefaults.Alpha.Hint else 1f),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = if (checked) TextDecoration.LineThrough else null,
            ),
        )

        AnimatedVisibility(visible = isFocused) {
            IconButton(
                onClick = { checklistSelection.onRemoveItem(item) }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Delete),
                    contentDescription = null
                )
            }
        }
    }
}

data class NoteDetailContentSelection(
    val onTitleTextChanged: (text: String) -> Unit,
    val onNoteTextChanged: (text: String) -> Unit,
    val onReminderClick: (reminder: Reminder) -> Unit,
    val onLabelClick: (label: Label) -> Unit
)

data class NoteDetailChecklistSelection(
    val onCheckedChange: (CheckItem, Boolean) -> Unit,
    val onChangeText: (CheckItem, String) -> Unit,
    val onRemoveItem: (CheckItem) -> Unit,
    val onNewItem: () -> Unit,
)