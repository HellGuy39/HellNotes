package com.hellguy39.hellnotes.feature.home.trash.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: TrashTopAppBarSelection,
    trashDropdownMenuSelection: TrashDropdownMenuSelection,
) {
    val trashDropdownMenuState = rememberDropdownMenuState()

    AnimatedContent(
        targetState = selection.selectedNotes.isNotEmpty(),
        label = "isNoteSelection",
    ) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text =
                        if (isNoteSelection) {
                            selection.selectedNotes.count().toString()
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
                        onClick = { selection.onCancelSelection() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Close),
                            contentDescription = stringResource(id = AppStrings.ContentDescription.Cancel),
                        )
                    }
                } else {
                    IconButton(
                        onClick = { selection.onNavigation() },
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
                        onClick = { selection.onRestoreSelected() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.RestoreFromTrash),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { selection.onDeleteSelected() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Delete),
                            contentDescription = null,
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            trashDropdownMenuState.show()
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.MoreVert),
                            contentDescription = null,
                        )

                        CustomDropdownMenu(
                            expanded = trashDropdownMenuState.visible,
                            onDismissRequest = { trashDropdownMenuState.dismiss() },
                            items =
                                listOf(
                                    CustomDropdownItemSelection(
                                        text = stringResource(id = AppStrings.MenuItem.EmptyTrash),
                                        onClick = {
                                            trashDropdownMenuState.dismiss()
                                            trashDropdownMenuSelection.onEmptyTrash()
                                        },
                                        leadingIconId = painterResource(id = AppIcons.Delete),
                                    ),
                                ),
                        )
                    }
                }
            },
        )
    }
}

data class TrashTopAppBarSelection(
    val selectedNotes: List<Note>,
    val onNavigation: () -> Unit,
    val onCancelSelection: () -> Unit,
    val onRestoreSelected: () -> Unit,
    val onDeleteSelected: () -> Unit,
)

data class TrashDropdownMenuSelection(
    val onEmptyTrash: () -> Unit,
)
