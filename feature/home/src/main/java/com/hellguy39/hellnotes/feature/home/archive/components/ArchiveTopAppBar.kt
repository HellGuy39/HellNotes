package com.hellguy39.hellnotes.feature.home.archive.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ArchiveTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: ArchiveTopAppBarSelection,
) {
    AnimatedContent(targetState = selection.selectedNotes.isNotEmpty()) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                if (isNoteSelection) {
                    Text(
                        text = stringResource(
                            id = HellNotesStrings.Text.Selected,
                            selection.selectedNotes.count()
                        ),
                        style = MaterialTheme.typography.headlineSmall
                    )
                } else {
                    Text(
                        "Archive",//stringResource(id ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                if(isNoteSelection) {
                    IconButton(
                        onClick = { selection.onCancelSelection() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
                        )
                    }
                } else {
                    IconButton(
                        onClick = { selection.onNavigation() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Menu),
                            contentDescription = null
                        )
                    }
                }
            },
            actions = {
                if (isNoteSelection) {
                    IconButton(
                        onClick = { selection.onArchiveSelected() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Unarchive),
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = { selection.onDeleteSelected() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                        )
                    }
                }
            }
        )
    }
}

data class ArchiveTopAppBarSelection(
    val selectedNotes: List<Note>,
    val onCancelSelection: () -> Unit,
    val onDeleteSelected: () -> Unit,
    val onArchiveSelected: () -> Unit,
    val onNavigation: () -> Unit,
)