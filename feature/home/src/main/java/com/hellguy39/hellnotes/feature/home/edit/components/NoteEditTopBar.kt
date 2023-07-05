package com.hellguy39.hellnotes.feature.home.edit.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hellguy39.hellnotes.core.model.ColorParam
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.feature.home.edit.NoteWrapperState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditTopBar(
    noteWrapperState: NoteWrapperState,
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBarSelection: NoteEditTopBarSelection,
) {
    if (noteWrapperState !is NoteWrapperState.Success)
        return

    val noteWrapper = noteWrapperState.noteWrapper

    val pinIcon = painterResource(
        id = if (noteWrapper.note.isPinned) HellNotesIcons.PinActivated else HellNotesIcons.PinDisabled
    )

    val archiveIcon = painterResource(
        id = if (noteWrapper.note.isArchived) HellNotesIcons.Unarchive else HellNotesIcons.Archive
    )

    val topAppBarColors = if (noteWrapper.note.colorHex == ColorParam.DefaultColor)
        TopAppBarDefaults.topAppBarColors()
    else
        TopAppBarDefaults.topAppBarColors(
            containerColor = Color(noteWrapper.note.colorHex),
            scrolledContainerColor = Color(noteWrapper.note.colorHex)
        )

    TopAppBar(
        colors = topAppBarColors,
        scrollBehavior = scrollBehavior,
        title = {},
        navigationIcon = {
            IconButton(onClick = topAppBarSelection.onNavigationButtonClick) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.ArrowBack),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = topAppBarSelection.onPin) {
                Icon(
                    painter = pinIcon,
                    contentDescription = null
                )
            }

            IconButton(onClick = topAppBarSelection.onReminder) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.NotificationAdd),
                    contentDescription = null
                )
            }

            IconButton(onClick = topAppBarSelection.onArchive) {
                Icon(
                    painter = archiveIcon,
                    contentDescription = null
                )
            }
        }
    )
}

data class NoteEditTopBarSelection(
    val onNavigationButtonClick: () -> Unit,
    val onPin: () -> Unit,
    val onArchive: () -> Unit,
    val onReminder: () -> Unit
)