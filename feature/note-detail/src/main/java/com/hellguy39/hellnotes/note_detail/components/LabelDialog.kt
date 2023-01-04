package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.CustomDialog
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.note_detail.events.LabelDialogEvents
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDialog(
    isShowDialog: Boolean,
    labels: List<Label>,
    note: Note,
    events: LabelDialogEvents
) {
    var query by remember { mutableStateOf("") }

    CustomDialog(
        showDialog = isShowDialog,
        onClose = { events.dismiss() },
        titleContent = {
            OutlinedTextField(
                value = query,
                onValueChange = { newText ->
                    query = newText
                    events.updateQuery(newText)
                },
                placeholder = {
                    Text(
                        text = stringResource(id = HellNotesStrings.Hint.Label),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge
            )
        },
        limitMaxHeight = true,
        applyBottomSpace = false
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (labels.isEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .clickable {
                                if (query.isNotBlank() && query.isNotEmpty())
                                    events.createLabel(query)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(id = HellNotesIcons.Add),
                            contentDescription = null
                        )

                        Text(
                            text = "Create new label",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }

            items(labels) { label ->
                val isSelected = note.labelIds.contains(label.id)
                Row(
                    modifier = Modifier
                        .clickable {
                            if (isSelected)
                                events.unselectLabel(label.id ?: return@clickable)
                            else
                                events.selectLabel(label.id ?: return@clickable)
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
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}