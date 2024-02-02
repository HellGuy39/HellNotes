package com.hellguy39.hellnotes.feature.notedetail.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.ColorParam
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.notedetail.NoteDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    topAppBarSelection: NoteDetailTopAppBarSelection,
    isReadOnly: Boolean,
) {
    val note = topAppBarSelection.uiState.wrapper.note

    val topAppBarColors =
        if (note.colorHex == ColorParam.DefaultColor) {
            TopAppBarDefaults.topAppBarColors()
        } else {
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color(note.colorHex),
                scrolledContainerColor = Color(note.colorHex),
            )
        }

    TopAppBar(
        colors = topAppBarColors,
        scrollBehavior = scrollBehavior,
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { topAppBarSelection.onNavigationButtonClick() },
            ) {
                Icon(
                    painter = painterResource(id = AppIcons.ArrowBack),
                    contentDescription = stringResource(id = AppStrings.ContentDescription.Back),
                )
            }
        },
        actions = {
            if (!isReadOnly) {
                IconButton(
                    onClick = { topAppBarSelection.onPin(!note.isPinned) },
                ) {
                    Icon(
                        painter = painterResource(AppIcons.pin(note.isPinned)),
                        contentDescription = stringResource(id = AppStrings.ContentDescription.Pin),
                    )
                }

                IconButton(
                    onClick = { topAppBarSelection.onReminder() },
                ) {
                    Icon(
                        painter = painterResource(id = AppIcons.NotificationAdd),
                        contentDescription = null,
                    )
                }

                IconButton(
                    onClick = { topAppBarSelection.onArchive(!note.isArchived) },
                ) {
                    Icon(
                        painter = painterResource(AppIcons.archive(!note.isArchived)),
                        contentDescription = null,
                    )
                }
            } else {
                FilterChip(
                    modifier =
                        Modifier
                            .height(FilterChipDefaults.Height)
                            .padding(horizontal = Spaces.medium),
                    selected = false,
                    onClick = {},
                    label = { Text(text = stringResource(id = AppStrings.Chip.ReadOnly)) },
                )
            }
        },
    )
}

data class NoteDetailTopAppBarSelection(
    val uiState: NoteDetailUiState,
    val onNavigationButtonClick: () -> Unit,
    val onPin: (isPinned: Boolean) -> Unit,
    val onArchive: (isArchived: Boolean) -> Unit,
    val onReminder: () -> Unit,
)
