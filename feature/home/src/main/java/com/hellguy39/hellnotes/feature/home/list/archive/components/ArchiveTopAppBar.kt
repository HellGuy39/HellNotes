package com.hellguy39.hellnotes.feature.home.list.archive.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ArchiveTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: ArchiveTopAppBarSelection,
    selectedNoteWrappers: List<NoteWrapper>
) {
    val listStyleIcon = if(selection.listStyle == ListStyle.Column)
        painterResource(id = HellNotesIcons.GridView)
    else
        painterResource(id = HellNotesIcons.ListView)

    AnimatedContent(targetState = selectedNoteWrappers.isNotEmpty()) { isNoteSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                if (isNoteSelection) {
                    Text(
                        text = selectedNoteWrappers.count().toString(),
                        style = MaterialTheme.typography.headlineSmall
                    )
                } else {
                    Text(
                        stringResource(id = HellNotesStrings.Title.Archive),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            navigationIcon = {
                if(isNoteSelection) {
                    IconButton(onClick = selection.onCancelSelection) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
                        )
                    }
                }
            },
            actions = {
                if (isNoteSelection) {
                    IconButton(onClick = selection.onUnarchiveSelected) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Unarchive),
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = selection.onDeleteSelected) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                        )
                    }
                }
            }
        )
    }
}

data class ArchiveTopAppBarSelection(
    val listStyle: ListStyle,
    val onSearch: () -> Unit,
    val onChangeListStyle: () -> Unit,
    val onCancelSelection: () -> Unit,
    val onDeleteSelected: () -> Unit,
    val onUnarchiveSelected: () -> Unit,
    val onNavigation: () -> Unit,
)