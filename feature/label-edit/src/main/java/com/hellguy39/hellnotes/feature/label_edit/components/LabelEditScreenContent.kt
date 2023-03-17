package com.hellguy39.hellnotes.feature.label_edit.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.ui.components.CustomDivider
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LabelEditScreenContent(
    paddingValues: PaddingValues,
    labels: List<Label>,
    labelItemSelection: LabelItemSelection,
    focusRequester: FocusRequester
) {
    var isFocused by remember { mutableStateOf(false) }
    var newLabel by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    LazyColumn(
        contentPadding = paddingValues
    ) {
        item {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Crossfade(isFocused) { isFocused ->
                    if (isFocused) {
                        IconButton(
                            onClick = {
                                newLabel = ""
                                focusManager.clearFocus()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.Close),
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.Add),
                                contentDescription = null
                            )
                        }
                    }
                }
                CustomTextField(
                    value = newLabel,
                    onValueChange = { newText -> newLabel = newText },
                    hint = stringResource(id = HellNotesStrings.Hint.CreateNewLabel),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .weight(1f)
                        .onFocusChanged { state -> isFocused = state.isFocused },
                    textStyle = MaterialTheme.typography.titleMedium
                )
                AnimatedVisibility (isFocused) {
                    IconButton(
                        onClick = {
                            if (newLabel.isNotBlank() && newLabel.isNotEmpty()) {
                                labelItemSelection.onCreateLabel(Label(name = newLabel))
                                newLabel = ""
                                focusManager.clearFocus()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Done),
                            contentDescription = null
                        )
                    }
                }
            }

            CustomDivider(isVisible = isFocused)
        }
        items(items = labels, key = { label -> label.id ?: 0 }) { label ->
            LabelItem(
                modifier = Modifier
                    .padding(4.dp)
                    .animateItemPlacement(),
                label = label,
                selection = labelItemSelection
            )
        }
    }
}