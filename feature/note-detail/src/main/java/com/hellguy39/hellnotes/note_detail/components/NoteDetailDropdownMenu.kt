package com.hellguy39.hellnotes.note_detail.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.note_detail.events.MenuEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun NoteDetailDropdownMenu(
    expanded: Boolean,
    noteDetailsMenuEvents: MenuEvents,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { noteDetailsMenuEvents.onDismissMenu() },
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.Color),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                with(noteDetailsMenuEvents) {
                    onDismissMenu()
                    onColor()
                }
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
                with(noteDetailsMenuEvents) {
                    onDismissMenu()
                    onLabels()
                }
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
                    text = stringResource(id = HellNotesStrings.Text.Share),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                with(noteDetailsMenuEvents) {
                    onDismissMenu()
                    onShare()
                }
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Share),
                    contentDescription = null
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
                with(noteDetailsMenuEvents) {
                    onDismissMenu()
                    onDelete()
                }
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