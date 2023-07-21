package com.hellguy39.hellnotes.feature.home.list.trash.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TrashTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: TrashTopAppBarSelection,
    selectedNoteWrappers: List<NoteWrapper>
) {
    AnimatedContent(targetState = selectedNoteWrappers.isNotEmpty()) {isNoteSelection  ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                if (isNoteSelection) {
                    Text(
                        text = selectedNoteWrappers.count().toString(),
                        style = MaterialTheme.typography.headlineSmall
                    )
                } else {
                    Text(
                        text = stringResource(id = HellNotesStrings.Title.Trash),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                if (isNoteSelection) {
                    IconButton(onClick = selection.onCancelSelection) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
                        )
                    }
                }
            },
            actions = {
                if (isNoteSelection) {
                    IconButton(onClick = selection.onRestoreSelected) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.RestoreFromTrash),
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = selection.onDeleteSelected) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(onClick = selection.onEmptyTrash) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.DeleteSweep),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

data class TrashTopAppBarSelection(
    val onNavigation: () -> Unit,
    val onCancelSelection: () -> Unit,
    val onRestoreSelected: () -> Unit,
    val onDeleteSelected: () -> Unit,
    val onEmptyTrash: () -> Unit
)