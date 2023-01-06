package com.hellguy39.hellnotes.notes.list.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.notes.list.events.TopAppBarEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarMenuEvents
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteListTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    isShowMenu: Boolean,
    topAppBarEvents: TopAppBarEvents,
    topAppBarMenuEvents: TopAppBarMenuEvents,
    selectedNotes: List<Note>
) {
    AnimatedContent(targetState = selectedNotes.isNotEmpty()) { targetState ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                if (targetState) {
                    IconButton(
                        onClick = { topAppBarEvents.onCancelSelection() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
                        )
                    }
                }
            },
            title = {
                if (targetState) {
                    Text(
                        text = stringResource(
                            id = HellNotesStrings.Text.Selected,
                            selectedNotes.count()
                        ),
                        style = MaterialTheme.typography.headlineSmall
                    )
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

                if (targetState) {
                    IconButton(
                        onClick = { topAppBarEvents.onDeleteAllSelected() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                        )
                    }
                } else {
                    Row {
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
                }

                NoteListDropdownMenu(
                    expanded = isShowMenu,
                    events = topAppBarMenuEvents
                )
            },
        )
    }

//    TopAppBar(
//        scrollBehavior = scrollBehavior,
//        navigationIcon = {
//            AnimatedVisibility(
//                visible = uiState.isSelection()
//            ) {
//                IconButton(
//                    onClick = { topAppBarEvents.onCancelSelection() }
//                ) {
//                    Icon(
//                        painter = painterResource(id = HellNotesIcons.Close),
//                        contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
//                    )
//                }
//            }
//        },
//        title = {
//            AnimatedContent(targetState = uiState.isSelection()) { targetState ->
//                if (targetState) {
//                    Text(
//                        text = stringResource(
//                            id = HellNotesStrings.Text.Selected,
//                            uiState.selectedCount()
//                        ),
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                } else {
//                    Text(
//                        text = buildAnnotatedString {
//                            append("Hell")
//                            withStyle(
//                                SpanStyle(
//                                    color = MaterialTheme.colorScheme.primary,
//                                )
//                            ) {
//                                append("Notes")
//                            }
//                        },
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                }
//            }
//        },
//        actions = {
//            AnimatedContent(targetState = uiState.isSelection()) { targetState ->
//                if (targetState) {
//                    IconButton(
//                        onClick = { topAppBarEvents.onDeleteAllSelected() }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = HellNotesIcons.Delete),
//                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
//                        )
//                    }
//                } else {
//                    Row {
//                        IconButton(
//                            onClick = { topAppBarEvents.onSearch() }
//                        ) {
//                            Icon(
//                                painter = painterResource(id = HellNotesIcons.Search),
//                                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Search)
//                            )
//                        }
//                        IconButton(
//                            onClick = { topAppBarMenuEvents.show() }
//                        ) {
//                            Icon(
//                                painter = painterResource(id = HellNotesIcons.MoreVert),
//                                contentDescription = stringResource(id = HellNotesStrings.ContentDescription.More)
//                            )
//                        }
//                    }
//                }
//            }
//
//            NoteListDropdownMenu(
//                expanded = isShowMenu,
//                events = topAppBarMenuEvents
//            )
//        },
//    )
}