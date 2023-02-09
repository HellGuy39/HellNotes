package com.hellguy39.hellnotes.feature.home.note_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.Sorting
import com.hellguy39.hellnotes.core.ui.components.CustomDialogState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun ListConfiguration(
    selection: ListConfigurationSelection,
    menuState: CustomDialogState
) {
    val sortName = when(selection.sorting) {
        is Sorting.DateOfLastEdit -> stringResource(id = HellNotesStrings.Text.DateOfLastEdit)
        is Sorting.DateOfCreation -> stringResource(id = HellNotesStrings.Text.DateOfCreation)
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        TextButton(
            modifier = Modifier.padding(start = 12.dp),
            onClick = { menuState.show() },
        ) {
            Icon(
                painter = painterResource(id = HellNotesIcons.Sort),
                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Sort)
            )
            Text(
                text = sortName,
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            SortDropdownMenu(
                expanded = menuState.visible,
                currentSorting = selection.sorting,
                onDismiss = {
                    menuState.dismiss()
                },
                onSortSelected = { sorting ->
                    selection.onSortingSelected(sorting)
                }
            )

        }
    }
}

data class ListConfigurationSelection(
    val sorting: Sorting,
    val onSortingSelected: (sorting: Sorting) -> Unit
)