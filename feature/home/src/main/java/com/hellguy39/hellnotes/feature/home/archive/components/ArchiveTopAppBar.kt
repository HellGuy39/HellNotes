package com.hellguy39.hellnotes.feature.home.archive.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ArchiveTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: ArchiveTopAppBarSelection,
) {
    val listStyleIcon =
        if (selection.listStyle == ListStyle.Column) {
            painterResource(id = HellNotesIcons.GridView)
        } else {
            painterResource(id = HellNotesIcons.ListView)
        }

    AnimatedContent(targetState = selection.selectedNotes.isNotEmpty()) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                if (isNoteSelection) {
                    Text(
                        text =
                            stringResource(
                                id = HellNotesStrings.Title.Selected,
                                selection.selectedNotes.count(),
                            ),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                } else {
                    Text(
                        stringResource(id = HellNotesStrings.Title.Archive),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            },
            navigationIcon = {
                if (isNoteSelection) {
                    IconButton(
                        onClick = { selection.onCancelSelection() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel),
                        )
                    }
                } else {
                    IconButton(
                        onClick = { selection.onNavigation() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Menu),
                            contentDescription = null,
                        )
                    }
                }
            },
            actions = {
                if (isNoteSelection) {
                    IconButton(
                        onClick = { selection.onUnarchiveSelected() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Unarchive),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { selection.onDeleteSelected() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete),
                        )
                    }
                } else {
                    IconButton(
                        onClick = { selection.onSearch() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Search),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { selection.onChangeListStyle() },
                    ) {
                        Icon(
                            painter = listStyleIcon,
                            contentDescription = null,
                        )
                    }
                }
            },
        )
    }
}

data class ArchiveTopAppBarSelection(
    val selectedNotes: List<Note>,
    val listStyle: ListStyle,
    val onSearch: () -> Unit,
    val onChangeListStyle: () -> Unit,
    val onCancelSelection: () -> Unit,
    val onDeleteSelected: () -> Unit,
    val onUnarchiveSelected: () -> Unit,
    val onNavigation: () -> Unit,
)
