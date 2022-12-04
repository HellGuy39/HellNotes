package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteListDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSettingsMenuItemClick: () -> Unit,
    onAboutAppMenuItemClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Settings),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onSettingsMenuItemClick()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Settings),
                    contentDescription = null
                )
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.AboutApp),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onAboutAppMenuItemClick()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Info),
                    contentDescription = null
                )
            }
        )
    }
}