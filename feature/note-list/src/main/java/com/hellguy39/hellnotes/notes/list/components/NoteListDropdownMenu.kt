package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.notes.list.events.TopAppBarMenuEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteListDropdownMenu(
    expanded: Boolean,
    events: TopAppBarMenuEvents,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { events.onDismiss() },
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.WhatsNew),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                events.onDismiss()
                events.onPatchNote()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Magic),
                    contentDescription = null
                )
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Settings),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                events.onDismiss()
                events.onSettings()
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
                events.onDismiss()
                events.onAboutApp()
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