package com.hellguy39.hellnotes.notes.detail.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteDetailDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onColorItemClick: () -> Unit,
    onLabelsItemClick: () -> Unit,
    onDeleteItemClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Color),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onColorItemClick()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Palette),
                    contentDescription = null
                )
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Labels),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onLabelsItemClick()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Label),
                    contentDescription = stringResource(id = HellNotesStrings.Text.Labels)
                )
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Delete),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onDeleteItemClick()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Delete),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                )
            }
        )
    }
}