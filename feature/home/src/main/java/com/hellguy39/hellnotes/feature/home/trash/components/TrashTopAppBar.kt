package com.hellguy39.hellnotes.feature.home.trash.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selectedNotes: SnapshotStateList<Note>,
    onNavigationClick: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onRestoreSelectedClick: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
    onEmptyTrashClick: () -> Unit,
) {
    AnimatedContent(
        targetState = selectedNotes.isNotEmpty(),
        label = "isNoteSelection",
    ) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text =
                        if (isNoteSelection) {
                            selectedNotes.count().toString()
                        } else {
                            stringResource(id = AppStrings.Title.Trash)
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
                        onClick = { onRestoreSelectedClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.RestoreFromTrash),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { onDeleteSelectedClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Delete),
                            contentDescription = null,
                        )
                    }
                } else {
                    IconButton(
                        onClick = { onEmptyTrashClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Delete),
                            contentDescription = null,
                        )
                    }
                }
            },
        )
    }
}
