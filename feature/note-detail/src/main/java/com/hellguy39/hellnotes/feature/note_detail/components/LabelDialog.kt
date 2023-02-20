package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.ui.components.CustomDialog
import com.hellguy39.hellnotes.core.ui.components.CustomDialogState
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.input.CustomTextField
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun LabelDialog(
    state: CustomDialogState,
    selection: LabelDialogSelection
) {
    var query by remember { mutableStateOf("") }

    CustomDialog(
        showDialog = state.visible,
        onClose = { state.dismiss() },
        titleContent = {
            CustomTextField(
                value = query,
                onValueChange = { newText ->
                    query = newText
                    selection.updateQuery(newText)
                },
                hint = stringResource(id = HellNotesStrings.Hint.Label),
                textStyle = MaterialTheme.typography.bodyLarge,
                isSingleLine = true
            )
        },
        limitMinHeight = true,
        limitMaxHeight = true,
        //applyBottomSpace = false
    ) {

        Crossfade(targetState = selection) { selection ->
            val isShowCreateNewLabelItem = isShowCreateNewLabelItem(selection.allLabels, query)

            if (!isShowCreateNewLabelItem && selection.allLabels.isEmpty()) {

                EmptyContentPlaceholder(
                    heroIcon = painterResource(id = HellNotesIcons.Label),
                    message = "Your labels will be displayed here",
                    heroIconSize = 64.dp
                )

                return@Crossfade
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selection.allLabels) { label ->
                    val isSelected = selection.note.labelIds.contains(label.id)
                    Row(
                        modifier = Modifier
                            .clickable {
                                if (isSelected)
                                    selection.unselectLabel(label)
                                else
                                    selection.selectLabel(label)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Spacer(modifier = Modifier.width(16.dp))

                        if (isSelected) {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.Done),
                                contentDescription = null
                            )
                        } else {
                            Spacer(modifier = Modifier.width(24.dp))
                        }
                        Text(
                            text = label.name,
                            modifier = Modifier
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = { selection.deleteLabel(label) }
                        ) {
                            Icon(
                                painter = painterResource(id = HellNotesIcons.Delete),
                                contentDescription = null
                            )
                        }
                    }
                }

                if (isShowCreateNewLabelItem) {
                    item {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    if (query.isNotBlank() && query.isNotEmpty())
                                        selection.createLabel(query)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Spacer(modifier = Modifier.width(16.dp))

                            Icon(
                                painter = painterResource(id = HellNotesIcons.NewLabel),
                                contentDescription = null
                            )

                            Text(
                                text = stringResource(id = HellNotesStrings.MenuItem.CreateNewLabel),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}

data class LabelDialogSelection(
    val note: Note,
    val allLabels: List<Label>,
    val selectLabel: (label: Label) -> Unit,
    val unselectLabel: (label: Label) -> Unit,
    val createLabel: (name: String) -> Unit,
    val updateQuery: (query: String) -> Unit,
    val deleteLabel: (label: Label) -> Unit
)

private fun isShowCreateNewLabelItem(allLabels: List<Label>, query: String): Boolean {
    return (allLabels.isEmpty() ||
            allLabels.size > 2 ||
            (allLabels.size == 1 && query != allLabels[0].name)) &&
            (query.isNotBlank() && query.isNotEmpty())
}