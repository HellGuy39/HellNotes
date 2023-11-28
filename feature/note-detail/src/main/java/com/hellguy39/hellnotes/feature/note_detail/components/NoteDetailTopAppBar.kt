package com.hellguy39.hellnotes.feature.note_detail.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.ColorParam
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_detail.NoteDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBarSelection: NoteDetailTopAppBarSelection,
) {
    val note = topAppBarSelection.uiState.wrapper.note

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
                onClick = { topAppBarSelection.onPin(!note.isPinned) }
            ) {
                Icon(
                    painter = painterResource(HellNotesIcons.pin(note.isPinned)),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Pin)
                )
            }

            IconButton(
                onClick = { topAppBarSelection.onReminder() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.NotificationAdd),
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { topAppBarSelection.onArchive(!note.isArchived) }
            ) {
                Icon(
                    painter = painterResource(HellNotesIcons.archive(!note.isArchived)),
                    contentDescription = null
                )
            }
        }
    )
}

data class NoteDetailTopAppBarSelection(
    val uiState: NoteDetailUiState,
    val onNavigationButtonClick: () -> Unit,
    val onPin: (isPinned: Boolean) -> Unit,
    val onArchive: (isArchived: Boolean) -> Unit,
    val onReminder: () -> Unit
)