package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.model.util.Sorting
import com.hellguy39.hellnotes.feature.note_list.NoteListUiState
import com.hellguy39.hellnotes.notes.list.events.SortMenuEvents
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun ListConfiguration(
    uiState: NoteListUiState.Success,
    sortMenuEvents: SortMenuEvents,
    isShowSortMenu: Boolean,
    onListStyleChange: () -> Unit
) {
    val sortName = when(uiState.sorting) {
        is Sorting.DateOfLastEdit -> stringResource(id = HellNotesStrings.Text.DateOfLastEdit)
        is Sorting.DateOfCreation -> stringResource(id = HellNotesStrings.Text.DateOfCreation)
    }

    val listStyleIcon = if(uiState.listStyle == ListStyle.Column)
        painterResource(id = HellNotesIcons.GridView)
    else
        painterResource(id = HellNotesIcons.ListView)

    Row(
        modifier = Modifier.fillMaxWidth(),
        //horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = { sortMenuEvents.show() },
        ) {
            Row {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Sort),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Sort)
                )
                Text(
                    text = stringResource(id = HellNotesStrings.Text.SortBy, sortName),
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                SortDropdownMenu(
                    expanded = isShowSortMenu,
                    events = sortMenuEvents,
                    currentSorting = uiState.sorting
                )
            }
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = { onListStyleChange() }
        ) {
            Icon(
                painter = listStyleIcon,
                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.ViewType)
            )
        }
    }
}