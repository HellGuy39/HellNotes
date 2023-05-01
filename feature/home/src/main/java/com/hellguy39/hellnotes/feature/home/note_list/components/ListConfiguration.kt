package com.hellguy39.hellnotes.feature.home.note_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.datastore.Sorting
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListConfiguration(
    selection: ListConfigurationSelection,
    menuState: CustomDropdownMenuState
) {
    val sortName = when(selection.sorting) {
        is Sorting.DateOfLastEdit -> stringResource(id = HellNotesStrings.Subtitle.DateOfLastEdit)
        is Sorting.DateOfCreation -> stringResource(id = HellNotesStrings.Subtitle.DateOfCreation)
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 4.dp)
            .padding(bottom = 6.dp, top = 2.dp)
    ) {
        InputChip(
            modifier = Modifier.height(InputChipDefaults.Height),
            selected = true,
            onClick = { menuState.show() },
            label = {
                Text(text = sortName)
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(InputChipDefaults.IconSize),
                    painter = painterResource(id = HellNotesIcons.Sort),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Sort),
                )
            },
            trailingIcon = {
                val icon = if (menuState.visible)
                    painterResource(HellNotesIcons.ArrowDropUp)
                else
                    painterResource(HellNotesIcons.ArrowDropDown)

                Icon(
                    modifier = Modifier.size(InputChipDefaults.IconSize),
                    painter = icon,
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Sort),
                )
            }
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

data class ListConfigurationSelection(
    val sorting: Sorting,
    val onSortingSelected: (sorting: Sorting) -> Unit
)