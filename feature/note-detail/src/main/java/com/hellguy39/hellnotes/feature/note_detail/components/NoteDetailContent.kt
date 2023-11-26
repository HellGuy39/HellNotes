package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.domain.ProjectInfoProvider
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.repository.local.database.*
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.HNIconButton
import com.hellguy39.hellnotes.core.ui.components.NoteChipGroup
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.components.rememberDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_detail.NoteDetailUiState
import com.hellguy39.hellnotes.feature.note_detail.R

@Composable
fun NoteDetailContent(
    innerPadding: PaddingValues,
    uiState: NoteDetailUiState.Success,
    selection: NoteDetailContentSelection,
    checklistSelection: NoteDetailChecklistSelection,
    focusRequester: FocusRequester,
    lazyListState: LazyListState
) {
    LaunchedEffect(key1 = uiState) {
        if (!uiState.wrapper.note.isNoteValid()) {
            focusRequester.requestFocus()
        }
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = innerPadding,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(
            key = -1
        ) {
            HNClearTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = uiState.wrapper.note.title,
                isSingleLine = false,
                onValueChange = { newText -> selection.onTitleTextChanged(newText) },
                hint = stringResource(id = HellNotesStrings.Hint.Title),
                textStyle = MaterialTheme.typography.titleLarge
            )
        }
        item(
            key = -2
        ) {
            HNClearTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester),
                value = uiState.wrapper.note.note,
                isSingleLine = false,
                onValueChange = { newText -> selection.onNoteTextChanged(newText) },
                hint = stringResource(id = HellNotesStrings.Hint.Note),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }

        items(
            items = uiState.wrapper.checklists,
            key = { checklist -> checklist.id ?: 0 }
        ) { checklist ->

            val isVisible = checklist.isExpanded
            val dropdownMenuState = rememberDropdownMenuState()

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp, alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { checklistSelection.onUpdateIsChecklistExpanded(checklist, !isVisible) }
                        ) {
                            val painterId = if (isVisible) HellNotesIcons.ExpandLess else HellNotesIcons.ExpandMore
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = painterId),
                                contentDescription = null
                            )
                        }

                        var isFocused by remember { mutableStateOf(false) }

                        HNClearTextField(
                            modifier = Modifier
                                .weight(1f)
                                .onFocusChanged { state -> isFocused = state.isFocused },
                            value = checklist.name,
                            onValueChange = { newText ->
                                checklistSelection.onChecklistNameChange(checklist, newText)
                            },
                            isSingleLine = true,
                            hint = stringResource(id = HellNotesStrings.Hint.NewChecklist)
                        )

                        IconButton(
                            modifier = Modifier.size(48.dp),
                            onClick = { dropdownMenuState.show() }
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = HellNotesIcons.MoreHoriz),
                                contentDescription = null
                            )

                            ChecklistDropdownMenu(
                                state = dropdownMenuState,
                                selection = ChecklistDropdownMenuSelection(
                                    onDeleteChecklist = {
                                        checklistSelection.onDelete(checklist)
                                    },
                                    onDoneAllItems = {
                                        checklistSelection.onDoneAll(checklist)
                                    },
                                    onRemoveDoneItems = {
                                        checklistSelection.onRemoveDone(checklist)
                                    }
                                )
                            )
                        }
                    }
                    AnimatedVisibility(visible = isVisible) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            checklist.items.forEach { item ->
                                CheckListItem(
                                    modifier = Modifier
                                        .clickable(
                                            role = Role.Checkbox,
                                            onClick = {
                                                checklistSelection.onCheckedChange(
                                                    checklist, item, !item.isChecked
                                                )
                                            }
                                        ),
                                    item = item,
                                    startPadding = 56.dp,
                                    paddingValues = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                                    onValueChange = { text ->
                                        checklistSelection.onUpdateChecklistItemText(
                                            checklist, item, text
                                        )
                                    },
                                    onDeleteItem = {
                                        checklistSelection.onDeleteChecklistItem(checklist, item)
                                    }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(UiDefaults.ListItem.DefaultHeight)
                                    .clickable { checklistSelection.onAddChecklistItem(checklist) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp),
                                        painter = painterResource(id = HellNotesIcons.Add),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        contentDescription = null
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(id = HellNotesStrings.Hint.AddNewItem),
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Start,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (uiState.wrapper.reminders.isNotEmpty() || uiState.wrapper.labels.isNotEmpty()) {
            item(
                key = -3
            ) {
                NoteChipGroup(
                    modifier = Modifier
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
                    mainAxisSpacing = 16.dp
                )
            }
        }

//        if (ProjectInfoProvider.appConfig.isDebug) {
//            val images = listOf(
//                com.hellguy39.hellnotes.core.ui.R.drawable.test_image,
//                com.hellguy39.hellnotes.core.ui.R.drawable.test_image,
//                com.hellguy39.hellnotes.core.ui.R.drawable.test_image,
//            )
//            item(
//                key = -4
//            ) {
//                LazyRow(
//                    modifier = Modifier,
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    contentPadding = PaddingValues(horizontal = 16.dp)
//                ) {
//                    items(images) { imageId ->
//                        Image(
//                            modifier = Modifier
//                                .height(256.dp)
//                                .width(256.dp)
//                                .clip(RoundedCornerShape(16.dp))
//                                .clickable(
//                                    onClick = {
//
//                                    }
//                                )
//                            ,
//                            painter = painterResource(id = imageId),
//                            contentScale = ContentScale.Crop,
//                            contentDescription = null
//                        )
//                    }
//                    item {
//                        ElevatedCard(
//                            modifier = Modifier
//                                .height(256.dp)
//                                .width(256.dp),
//                            shape = RoundedCornerShape(16.dp),
//                            onClick = {}
//                        ) {
//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Icon(
//                                    modifier = Modifier.size(48.dp),
//                                    painter = painterResource(id = HellNotesIcons.Add),
//                                    tint = MaterialTheme.colorScheme.primary,
//                                    contentDescription = null
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
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
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = startPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                modifier = Modifier,
                checked = item.isChecked,
                onCheckedChange = null
            )

            Spacer(modifier = Modifier.width(16.dp))

            HNClearTextField(
                value = item.text,
                modifier = Modifier
                    .onFocusChanged { state -> isFocused = state.isFocused }
                    .weight(1f)
                    .alpha(if (item.isChecked) UiDefaults.Alpha.Hint else 1f),
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (item.isChecked) TextDecoration.LineThrough else null
                ),
                hint = stringResource(id = HellNotesStrings.Hint.Item),
            )

            HNIconButton(
                enabled = isFocused,
                onClick = onDeleteItem,
                enabledPainter = painterResource(id = HellNotesIcons.Delete),
                containerSize = 48.dp
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

data class NoteDetailChecklistSelection(
    val onCheckedChange: (Checklist, ChecklistItem, Boolean) -> Unit,
    val onAddChecklistItem: (Checklist) -> Unit,
    val onDeleteChecklistItem: (Checklist, ChecklistItem) -> Unit,
    val onChecklistNameChange: (Checklist, String) -> Unit,
    val onUpdateChecklistItemText: (Checklist, ChecklistItem, String) -> Unit,
    val onUpdateIsChecklistExpanded: (Checklist, Boolean) -> Unit,
    val onDoneAll: (Checklist) -> Unit,
    val onRemoveDone: (Checklist) -> Unit,
    val onDelete: (Checklist) -> Unit
)