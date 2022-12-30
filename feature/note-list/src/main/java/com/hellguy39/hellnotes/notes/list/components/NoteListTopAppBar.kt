package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.hellguy39.hellnotes.notes.list.events.TopAppBarEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarMenuEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    isShowMenu: Boolean,
    topAppBarEvents: TopAppBarEvents,
    topAppBarMenuEvents: TopAppBarMenuEvents
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
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
        },
        actions = {
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

            NoteListDropdownMenu(
                expanded = isShowMenu,
                events = topAppBarMenuEvents
            )
        },
    )
}