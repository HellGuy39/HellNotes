package com.hellguy39.hellnotes.feature.notedetail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.database.Reminder
import com.hellguy39.hellnotes.core.model.repository.local.database.hasContentText
import com.hellguy39.hellnotes.core.ui.components.HNIconButton
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.components.rememberDropdownMenuState
import com.hellguy39.hellnotes.core.ui.focus.requestFocusWhen
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Alpha
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailUiState

@Composable
fun NoteDetailContent(
    innerPadding: PaddingValues,
    uiState: NoteDetailUiState,
    selection: NoteDetailContentSelection,
    checklistSelection: NoteDetailChecklistSelection,
    focusRequester: FocusRequester,
    lazyListState: LazyListState,
) {
    focusRequester.requestFocusWhen { !uiState.wrapper.note.hasContentText() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding =
            PaddingValues(
                top = innerPadding.calculateTopPadding() + Spaces.medium,
                bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
            ),
        verticalArrangement = Arrangement.spacedBy(Spaces.medium),
    ) {
        item(-1) {
            HNClearTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                value = uiState.wrapper.note.title,
                isSingleLine = false,
                onValueChange = { newText -> selection.onTitleTextChanged(newText) },
                hint = stringResource(id = AppStrings.Hint.Title),
                textStyle = MaterialTheme.typography.titleLarge,
            )
        }
        item(-2) {
            HNClearTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .focusRequester(focusRequester),
                value = uiState.wrapper.note.note,
                isSingleLine = false,
                onValueChange = { newText -> selection.onNoteTextChanged(newText) },
                hint = stringResource(id = AppStrings.Hint.Note),
                textStyle = MaterialTheme.typography.bodyLarge,
            )
        }

        items(
            items = uiState.wrapper.checklists,
            key = { checklist -> checklist.id ?: 0 },
        ) { checklist ->

            val isVisible = checklist.isExpanded
            val dropdownMenuState = rememberDropdownMenuState()

            Card(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                        horizontalArrangement =
                            Arrangement.spacedBy(
                                space = 8.dp,
                                alignment = Alignment.CenterHorizontally,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { checklistSelection.onUpdateIsChecklistExpanded(checklist, !isVisible) },
                        ) {
                            val painterId = if (isVisible) AppIcons.ExpandLess else AppIcons.ExpandMore
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = painterId),
                                contentDescription = null,
                            )
                        }

                        var isFocused by remember { mutableStateOf(false) }

                        HNClearTextField(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .onFocusChanged { state -> isFocused = state.isFocused },
                            value = checklist.name,
                            onValueChange = { newText ->
                                checklistSelection.onChecklistNameChange(checklist, newText)
                            },
                            isSingleLine = true,
                            hint = stringResource(id = AppStrings.Hint.NewChecklist),
                        )

                        IconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { dropdownMenuState.show() },
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = AppIcons.MoreHoriz),
                                contentDescription = null,
                            )

                            ChecklistDropdownMenu(
                                state = dropdownMenuState,
                                selection =
                                    ChecklistDropdownMenuSelection(
                                        onDeleteChecklist = {
                                            checklistSelection.onDelete(checklist)
                                        },
                                        onDoneAllItems = {
                                            checklistSelection.onDoneAll(checklist)
                                        },
                                        onRemoveDoneItems = {
                                            checklistSelection.onRemoveDone(checklist)
                                        },
                                    ),
                            )
                        }
                    }
                    AnimatedVisibility(visible = isVisible) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            checklist.items.forEach { item ->
                                CheckListItem(
                                    modifier =
                                        Modifier
                                            .clickable(
                                                role = Role.Checkbox,
                                                onClick = {
                                                    checklistSelection.onCheckedChange(
                                                        checklist,
                                                        item,
                                                        !item.isChecked,
                                                    )
                                                },
                                            ),
                                    item = item,
                                    startPadding = 56.dp,
                                    paddingValues = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                                    onValueChange = { text ->
                                        checklistSelection.onUpdateChecklistItemText(
                                            checklist,
                                            item,
                                            text,
                                        )
                                    },
                                    onDeleteItem = {
                                        checklistSelection.onDeleteChecklistItem(checklist, item)
                                    },
                                )
                            }
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .size(56.dp)
                                        .clickable { checklistSelection.onAddChecklistItem(checklist) },
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier =
                                        Modifier
                                            .size(48.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        modifier =
                                            Modifier
                                                .size(24.dp),
                                        painter = painterResource(id = AppIcons.Add),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        contentDescription = null,
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(id = AppStrings.Hint.AddNewItem),
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (uiState.wrapper.reminders.isNotEmpty() || uiState.wrapper.labels.isNotEmpty()) {
            item(-3) {
                NoteChipGroup(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    reminders = uiState.wrapper.reminders,
                    labels = uiState.wrapper.labels,
                    onRemindClick = { remind ->
                        selection.onReminderClick(remind)
                    },
                    onLabelClick = { label ->
                        selection.onLabelClick(label)
                    },
                    crossAxisSpacing = 16.dp,
                    mainAxisSpacing = 16.dp,
                )
            }
        }
    }
}

@Composable
fun CheckListItem(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    startPadding: Dp = 0.dp,
    item: ChecklistItem,
    onDeleteItem: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
    ) {
        Row(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(start = startPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                modifier = Modifier,
                checked = item.isChecked,
                onCheckedChange = null,
            )

            Spacer(modifier = Modifier.width(16.dp))

            HNClearTextField(
                value = item.text,
                modifier =
                    Modifier
                        .onFocusChanged { state -> isFocused = state.isFocused }
                        .weight(1f)
                        .alpha(if (item.isChecked) Alpha.HINT else 1f),
                onValueChange = onValueChange,
                textStyle =
                    MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = if (item.isChecked) TextDecoration.LineThrough else null,
                    ),
                hint = stringResource(id = AppStrings.Hint.Item),
            )

            HNIconButton(
                enabled = isFocused,
                onClick = onDeleteItem,
                enabledPainter = painterResource(id = AppIcons.Delete),
                containerSize = 48.dp,
            )
        }
    }
}

data class NoteDetailContentSelection(
    val onTitleTextChanged: (text: String) -> Unit,
    val onNoteTextChanged: (text: String) -> Unit,
    val onReminderClick: (reminder: Reminder) -> Unit,
    val onLabelClick: (label: Label) -> Unit,
)

data class NoteDetailChecklistSelection(
    val onCheckedChange: (Checklist, ChecklistItem, Boolean) -> Unit,
    val onAddChecklistItem: (Checklist) -> Unit,
    val onDeleteChecklistItem: (Checklist, ChecklistItem) -> Unit,
    val onChecklistNameChange: (Checklist, String) -> Unit,
    val onUpdateChecklistItemText: (Checklist, ChecklistItem, String) -> Unit,
    val onUpdateIsChecklistExpanded: (Checklist, Boolean) -> Unit,
    val onDoneAll: (Checklist) -> Unit,
    val onRemoveDone: (Checklist) -> Unit,
    val onDelete: (Checklist) -> Unit,
)
