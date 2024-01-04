package com.hellguy39.hellnotes.feature.home.label.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.components.rememberDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: LabelTopAppBarSelection,
    label: Label,
    dropdownMenuSelection: LabelDropdownMenuSelection,
) {
    val listStyleIcon =
        if (selection.listStyle == ListStyle.Column) {
            painterResource(id = AppIcons.GridView)
        } else {
            painterResource(id = AppIcons.ListView)
        }

    val labelDropdownMenuState = rememberDropdownMenuState()

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
                            label.name
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
                        onClick = { selection.onArchiveSelected() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Archive),
                            contentDescription = null,
                        )
                    }
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
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = { labelDropdownMenuState.show() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.MoreVert),
                            contentDescription = stringResource(id = AppStrings.ContentDescription.More),
                        )
                        LabelDropdownMenu(
                            state = labelDropdownMenuState,
                            selection = dropdownMenuSelection,
                        )
                    }
                }
            },
        )
    }
}

data class LabelTopAppBarSelection(
    val selectedNotes: List<Note>,
    val listStyle: ListStyle,
    val onSearch: () -> Unit,
    val onChangeListStyle: () -> Unit,
    val onArchiveSelected: () -> Unit,
    val onCancelSelection: () -> Unit,
    val onDeleteSelected: () -> Unit,
    val onNavigation: () -> Unit,
)
