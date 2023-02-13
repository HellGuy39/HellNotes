package com.hellguy39.hellnotes.feature.home.note_list.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteListTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selectedNotes: List<Note>,
    selection: NoteListTopAppBarSelection,
) {
    val listStyleIcon = if(selection.listStyle == ListStyle.Column)
        painterResource(id = HellNotesIcons.GridView)
    else
        painterResource(id = HellNotesIcons.ListView)

    AnimatedContent(targetState = selectedNotes.isNotEmpty()) { targetState ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                if (targetState) {
                    IconButton(
                        onClick = { selection.onCancelSelection() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel)
                        )
                    }
                }
            },
            title = {
                if (targetState) {
                    Text(
                        text = stringResource(
                            id = HellNotesStrings.Text.Selected,
                            selectedNotes.count()
                        ),
                        style = MaterialTheme.typography.headlineSmall
                    )
                } else {

                    ElevatedCard(
                        onClick = { selection.onSearch() },
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                modifier = Modifier.padding(vertical = 4.dp),
                                onClick = { selection.onNavigation() }
                            ) {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Menu),
                                    contentDescription = null
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .alpha(UiDefaults.Alpha.Hint),
                                text = stringResource(
                                    id = HellNotesStrings.Hint.Search,
                                ),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                modifier = Modifier.padding(vertical = 4.dp),
                                onClick = { selection.onChangeListStyle() }
                            ) {
                                Icon(
                                    painter = listStyleIcon,
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }

                }

            },
            actions = {
                if (targetState) {
                    IconButton(
                        onClick = { selection.onArchive() }
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
                    Spacer(modifier = Modifier.width(16.dp))
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