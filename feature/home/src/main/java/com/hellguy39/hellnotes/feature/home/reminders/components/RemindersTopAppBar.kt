package com.hellguy39.hellnotes.feature.home.reminders.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: ReminderTopAppBarSelection,
) {
    val listStyleIcon =
        if (selection.listStyle == ListStyle.Column) {
            painterResource(id = AppIcons.GridView)
        } else {
            painterResource(id = AppIcons.ListView)
        }

    AnimatedContent(
        targetState = selection.selectedNotes.isNotEmpty(),
        label = "isNoteSelection",
    ) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    if (isNoteSelection) {
                        selection.selectedNotes.count().toString()
                    } else {
                        stringResource(id = AppStrings.Title.Reminders)
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
                        onClick = { selection.onDeleteSelected() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Delete),
                            contentDescription = stringResource(id = AppStrings.ContentDescription.Delete),
                        )
                    }
                } else {
                    IconButton(
                        onClick = { selection.onSearch() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Search),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { selection.onChangeListStyle() },
                    ) {
                        Icon(
                            painter = listStyleIcon,
                            contentDescription = null,
                        )
                    }
                }
            },
        )
    }
}

data class ReminderTopAppBarSelection(
    val listStyle: ListStyle,
    val selectedNotes: List<Note>,
    val onNavigation: () -> Unit,
    val onSearch: () -> Unit,
    val onChangeListStyle: () -> Unit,
    val onCancelSelection: () -> Unit,
    val onDeleteSelected: () -> Unit,
)
