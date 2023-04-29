package com.hellguy39.hellnotes.feature.home.note_list.components

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Sorting
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun SortDropdownMenu(
    expanded: Boolean,
    currentSorting: Sorting,
    onDismiss: () -> Unit,
    onSortSelected: (Sorting) -> Unit
) {
    DropdownMenu(
        modifier = Modifier,
        expanded = expanded,
        onDismissRequest = { onDismiss() },
    ) {
        DropdownMenuItem(
            modifier = Modifier.background(
                color = if (currentSorting == Sorting.DateOfCreation)
                    MaterialTheme.colorScheme.secondaryContainer
                else
                    MaterialTheme.colorScheme.surfaceColorAtElevation(UiDefaults.Elevation.Level2)
            ),
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Subtitle.DateOfCreation),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onSortSelected(Sorting.DateOfCreation)
            },
        )
        DropdownMenuItem(
            modifier = Modifier.background(
                color = if (currentSorting == Sorting.DateOfLastEdit)
                    MaterialTheme.colorScheme.secondaryContainer
                else
                    MaterialTheme.colorScheme.surfaceColorAtElevation(UiDefaults.Elevation.Level2)
            ),
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Subtitle.DateOfLastEdit),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onSortSelected(Sorting.DateOfLastEdit)
            },
        )
    }
}