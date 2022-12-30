package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.hellguy39.hellnotes.notes.list.NoteListUiState
import com.hellguy39.hellnotes.notes.list.events.TopAppBarEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarMenuEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListTopAppBar(
    uiState: NoteListUiState,
    scrollBehavior: TopAppBarScrollBehavior,
    isShowMenu: Boolean,
    topAppBarEvents: TopAppBarEvents,
    topAppBarMenuEvents: TopAppBarMenuEvents
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            if (uiState.isSelection()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { topAppBarEvents.onCancelSelection() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
                        )
                    }
                    Text(
                        text = stringResource(
                            id = HellNotesStrings.Text.Selected,
                            uiState.selectedCount()
                        ),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            } else {
                Text(
                    text = buildAnnotatedString {
                        append("Hell")
                        withStyle(
                            SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append("Notes")
                        }
                    },
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },
        actions = {
            if (uiState.isSelection()) {
                IconButton(
                    onClick = { topAppBarEvents.onDeleteAllSelected() }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Delete),
                        contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                    )
                }
            } else {
                IconButton(
                    onClick = { topAppBarEvents.onSearch() }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Search),
                        contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Search)
                    )
                }
                IconButton(
                    onClick = { topAppBarMenuEvents.show() }
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.MoreVert),
                        contentDescription = stringResource(id = HellNotesStrings.ContentDescription.More)
                    )
                }
            }

            NoteListDropdownMenu(
                expanded = isShowMenu,
                events = topAppBarMenuEvents
            )
        },
    )
}