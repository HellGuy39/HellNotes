package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.rememberDropdownMenuState
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.util.ColorParam
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBarSelection: NoteDetailTopAppBarSelection,
    dropdownMenuSelection: NoteDetailDropdownMenuSelection
) {
    val noteDetailDropdownMenuState = rememberDropdownMenuState()

    val note = topAppBarSelection.note

    val pinIcon = painterResource(
        id = if (note.isPinned) HellNotesIcons.PinActivated else HellNotesIcons.PinDisabled
    )

    val archiveIcon = painterResource(
        id = if (note.isArchived) HellNotesIcons.Unarchive else HellNotesIcons.Archive
    )

    val topAppBarColors = if (note.colorHex == ColorParam.DefaultColor)
        TopAppBarDefaults.topAppBarColors()
    else
        TopAppBarDefaults.topAppBarColors(
            containerColor = Color(note.colorHex),
            scrolledContainerColor = Color(note.colorHex)
        )

    TopAppBar(
        colors = topAppBarColors,
        scrollBehavior = scrollBehavior,
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { topAppBarSelection.onNavigationButtonClick() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.ArrowBack),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Back)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { topAppBarSelection.onReminder() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Notifications),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Reminder)
                )
            }
            IconButton(
                onClick = { topAppBarSelection.onArchive(!note.isArchived) }
            ) {
                Icon(
                    painter = archiveIcon,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = { topAppBarSelection.onPin(!note.isPinned) }
            ) {
                Icon(
                    painter = pinIcon,
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Pin)
                )
            }
            IconButton(
                onClick = { noteDetailDropdownMenuState.show() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.MoreVert),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.More)
                )

                NoteDetailDropdownMenu(
                    state = noteDetailDropdownMenuState,
                    selection = dropdownMenuSelection
                )
            }
        }
    )
}

data class NoteDetailTopAppBarSelection(
    val note: Note,
    val onNavigationButtonClick: () -> Unit,
    val onReminder: () -> Unit,
    val onPin: (isPinned: Boolean) -> Unit,
    val onArchive: (isArchived: Boolean) -> Unit,
    val onColorSelected: (colorHex: Long) -> Unit,
)