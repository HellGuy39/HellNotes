package com.hellguy39.hellnotes.feature.home.note_list.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.util.Sorting
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun SortDropdownMenu(
    expanded: Boolean,
    currentSorting: Sorting,
    onDismiss: () -> Unit,
    onSortSelected: (Sorting) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = HellNotesStrings.Text.DateOfCreation),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onClick = {
                onDismiss()
                onSortSelected(Sorting.DateOfCreation)
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
                onDismiss()
                onSortSelected(Sorting.DateOfLastEdit)
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