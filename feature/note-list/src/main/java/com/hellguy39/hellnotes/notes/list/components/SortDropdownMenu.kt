package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.model.util.Sorting
import com.hellguy39.hellnotes.notes.list.events.SortMenuEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@Composable
fun SortDropdownMenu(
    expanded: Boolean,
    events: SortMenuEvents,
    currentSorting: Sorting
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { events.onDismiss() },
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.DateOfCreation),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                events.onDismiss()
                events.onSortSelected(sorting = Sorting.DateOfCreation)
            },
            leadingIcon = {
                if (currentSorting == Sorting.DateOfCreation) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Done),
                        contentDescription = null,
                    )
                }
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.DateOfLastEdit),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                events.onDismiss()
                events.onSortSelected(sorting = Sorting.DateOfLastEdit)
            },
            leadingIcon = {
                if (currentSorting == Sorting.DateOfLastEdit) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Done),
                        contentDescription = null
                    )
                }
            }
        )
    }
}