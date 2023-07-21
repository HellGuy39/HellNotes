package com.hellguy39.hellnotes.feature.home.list.notes.components

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
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteListTopAppBar(
    selectedNoteWrappers: List<NoteWrapper>,
    scrollBehavior: TopAppBarScrollBehavior,
    selection: NoteListTopAppBarSelection,
) {
    val listStyleIcon = if(selection.listStyle == ListStyle.Column)
        painterResource(id = HellNotesIcons.GridView)
    else
        painterResource(id = HellNotesIcons.ListView)

    AnimatedContent(
        targetState = selectedNoteWrappers.isNotEmpty()
    ) { isSelection ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                if (isSelection) {
                    IconButton(onClick = selection.onCancelSelection) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
                        )
                    }
                } else {
                    IconButton(onClick = selection.onNavigation) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Menu),
                            contentDescription = null
                        )
                    }
                }
            },
            title = {
                val text = if (isSelection)
                    selectedNoteWrappers.count().toString()
                else
                    "Notes"
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            actions = {
                if (isSelection) {
                    IconButton(onClick = selection.onArchive) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Archive),
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = selection.onDeleteSelected) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete)
                        )
                    }
                } else {
                    IconButton(onClick = selection.onSearch) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Search),
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = selection.onChangeListStyle) {
                        Icon(
                            painter = listStyleIcon,
                            contentDescription = null
                        )
                    }
                }
            },
        )
    }
}

data class NoteListTopAppBarSelection(
    val listStyle: ListStyle,
    val onCancelSelection: () -> Unit,
    val onDeleteSelected: () -> Unit,
    val onArchive: () -> Unit,
    val onSearch: () -> Unit,
    val onNavigation: () -> Unit,
    val onChangeListStyle: () -> Unit
)