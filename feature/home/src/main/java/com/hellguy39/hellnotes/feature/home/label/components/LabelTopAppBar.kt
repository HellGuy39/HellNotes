package com.hellguy39.hellnotes.feature.home.label.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.components.rememberDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.home.label.LabelUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    uiState: LabelUiState,
    listStyle: ListStyle,
    onSearchClick: () -> Unit,
    onToggleListStyle: () -> Unit,
    onArchiveSelectedClick: () -> Unit,
    onCancelSelectionClick: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
    onNavigationClick: () -> Unit,
    onRenameClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val labelDropdownMenuState = rememberDropdownMenuState()

    AnimatedContent(
        targetState = uiState.isNoteSelection,
        label = "isNoteSelection",
    ) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text =
                        if (isNoteSelection) {
                            uiState.countOfSelectedNotes.toString()
                        } else {
                            uiState.label.name
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
                        onClick = { onArchiveSelectedClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Archive),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { onDeleteSelectedClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Delete),
                            contentDescription = stringResource(id = AppStrings.ContentDescription.Delete),
                        )
                    }
                } else {
                    IconButton(
                        onClick = { onSearchClick() },
                    ) {
                        Icon(
                            painter = painterResource(id = AppIcons.Search),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { onToggleListStyle() },
                    ) {
                        Icon(
                            painter =
                                if (listStyle == ListStyle.Column) {
                                    painterResource(id = AppIcons.GridView)
                                } else {
                                    painterResource(id = AppIcons.ListView)
                                },
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
                            onDeleteClick = onDeleteClick,
                            onRenameClick = onRenameClick,
                        )
                    }
                }
            },
        )
    }
}
