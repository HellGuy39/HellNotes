package com.hellguy39.hellnotes.feature.home.trash.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownItem
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.components.rememberDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TrashTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: TrashTopAppBarSelection,
    trashDropdownMenuSelection: TrashDropdownMenuSelection
) {

    val trashDropdownMenuState = rememberDropdownMenuState()

    AnimatedContent(targetState = selection.selectedNotes.isNotEmpty()) {isNoteSelection  ->

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
                        text = "Trash",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                if (isNoteSelection) {
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
                        onClick = { selection.onRestoreSelected() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.RestoreFromTrash),
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            trashDropdownMenuState.show()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.MoreVert),
                            contentDescription = null
                        )

                        TrashDropdownMenu(
                            state = trashDropdownMenuState,
                            selection = trashDropdownMenuSelection
                        )
                    }
                }
            }
        )
    }
}

data class TrashTopAppBarSelection(
    val selectedNotes: List<Note>,
    val onNavigation: () -> Unit,
    val onCancelSelection: () -> Unit,
    val onRestoreSelected: () -> Unit
)

@Composable
fun TrashDropdownMenu(
    state: CustomDropdownMenuState,
    selection: TrashDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() }
    ) {
        CustomDropdownItem(
            text = "Empty trash",
            onClick = {
                state.dismiss()
                selection.onEmptyTrash()
            },
            leadingIconId = painterResource(id = HellNotesIcons.Delete)
        )
    }
}

data class TrashDropdownMenuSelection(
    val onEmptyTrash: () -> Unit,
)