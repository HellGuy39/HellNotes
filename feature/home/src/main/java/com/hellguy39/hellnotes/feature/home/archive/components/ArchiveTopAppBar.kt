package com.hellguy39.hellnotes.feature.home.archive.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selectedNotes: SnapshotStateList<Note>,
    listStyle: ListStyle,
    onSearchClick: () -> Unit,
    onToggleListStyle: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
    onUnarchiveSelectedClick: () -> Unit,
    onNavigationClick: () -> Unit,
) {
    AnimatedContent(
        targetState = selectedNotes.isNotEmpty(),
        label = "isSelection",
    ) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text =
                        if (isNoteSelection) {
                            selectedNotes.count().toString()
                        } else {
                            stringResource(id = AppStrings.Title.Archive)
                        },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            navigationIcon = {
                if (isNoteSelection) {
                    IconButton(
                        onClick = { onCancelSelectionClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Close),
                            contentDescription = stringResource(id = AppStrings.ContentDescription.Cancel),
                        )
                    }
                } else {
                    IconButton(
                        onClick = { onNavigationClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Menu),
                            contentDescription = null,
                        )
                    }
                }
            },
            actions = {
                if (isNoteSelection) {
                    IconButton(
                        onClick = { onUnarchiveSelectedClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Unarchive),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { onDeleteSelectedClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Delete),
                            contentDescription = stringResource(id = AppStrings.ContentDescription.Delete),
                        )
                    }
                } else {
                    IconButton(
                        onClick = { onSearchClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Search),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { onToggleListStyle() },
                    ) {
                        Icon(
                            painter =
                                if (listStyle == ListStyle.Column) {
                                    painterResource(id = AppIcons.GridView)
                                } else {
                                    painterResource(id = AppIcons.ListView)
                                },
                            contentDescription = null,
                        )
                    }
                }
            },
        )
    }
}
