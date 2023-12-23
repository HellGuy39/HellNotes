package com.hellguy39.hellnotes.feature.home.notelist.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Alpha
import com.hellguy39.hellnotes.core.ui.values.Elevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selectedNotes: List<Note>,
    selection: NoteListTopAppBarSelection,
) {
    val listStyleIcon =
        if (selection.listStyle == ListStyle.Column) {
            painterResource(id = HellNotesIcons.GridView)
        } else {
            painterResource(id = HellNotesIcons.ListView)
        }

    AnimatedContent(
        targetState = selectedNotes.isNotEmpty(),
        label = "top_app_bar_animate_state",
    ) { targetState ->
        TopAppBar(
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                if (targetState) {
                    IconButton(
                        onClick = { selection.onCancelSelection() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Close),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Cancel),
                        )
                    }
                }
            },
            title = {
                if (targetState) {
                    Text(
                        text =
                            stringResource(
                                id = HellNotesStrings.Title.Selected,
                                selectedNotes.count(),
                            ),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                } else {
                    Card(
                        onClick = { selection.onSearch() },
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme.colorScheme
                                        .surfaceColorAtElevation(Elevation.Level3),
                            ),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                modifier = Modifier.padding(vertical = 0.dp),
                                onClick = { selection.onNavigation() },
                            ) {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Menu),
                                    contentDescription = null,
                                )
                            }
                            Text(
                                modifier =
                                    Modifier
                                        .alpha(Alpha.HINT),
                                text =
                                    stringResource(
                                        id = HellNotesStrings.Hint.Search,
                                    ),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                modifier = Modifier.padding(vertical = 0.dp),
                                onClick = { selection.onChangeListStyle() },
                            ) {
                                Icon(
                                    painter = listStyleIcon,
                                    contentDescription = null,
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
                        onClick = { selection.onArchive() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Archive),
                            contentDescription = null,
                        )
                    }
                    IconButton(
                        onClick = { selection.onDeleteSelected() },
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.Delete),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Delete),
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
    val onChangeListStyle: () -> Unit,
)
