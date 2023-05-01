package com.hellguy39.hellnotes.feature.home.label.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LabelTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: LabelTopAppBarSelection,
    label: Label,
    dropdownMenuSelection: LabelDropdownMenuSelection
) {
    val listStyleIcon = if(selection.listStyle == ListStyle.Column)
        painterResource(id = HellNotesIcons.GridView)
    else
        painterResource(id = HellNotesIcons.ListView)

    val labelDropdownMenuState = rememberDropdownMenuState()

    AnimatedContent(targetState = selection.selectedNotes.isNotEmpty()) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                if (isNoteSelection) {
                    Text(
                        text = stringResource(
                            id = HellNotesStrings.Title.Selected,
                            selection.selectedNotes.count()
                        ),
                        style = MaterialTheme.typography.headlineSmall
                    )
                } else {
                    Text(
                        label.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                if(isNoteSelection) {
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
                        onClick = { selection.onArchiveSelected() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Archive),
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = { selection.onDeleteSelected() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                        )
                    }
                } else {
                    IconButton(
                        onClick = { selection.onSearch() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Search),
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = { selection.onChangeListStyle() }
                    ) {
                        Icon(
                            painter = listStyleIcon,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        modifier = Modifier.size(48.dp),
                        onClick = { labelDropdownMenuState.show() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.MoreVert),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.More)
                        )
                        LabelDropdownMenu(
                            state = labelDropdownMenuState,
                            selection = dropdownMenuSelection
                        )
                    }
                }
            }
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