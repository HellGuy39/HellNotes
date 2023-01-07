package com.hellguy39.hellnotes.search.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.components.NoteCard
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings
import com.hellguy39.hellnotes.search.UiState

@Composable
fun SearchScreenContent(
    uiState: UiState,
    innerPadding: PaddingValues,
    listStyle: ListStyle,
    allLabels: List<Label>,
    allReminds: List<Remind>,
    onNoteClick: (note: Note) -> Unit
) {
    Crossfade(targetState = uiState) { state ->
        when(state) {
            is UiState.Success -> {
                if (state.notes.isEmpty()) {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.Search),
                        message = stringResource(id = HellNotesStrings.Text.NothingWasFound)
                    )
                } else {
                    when(listStyle) {
                        is ListStyle.Column -> {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp, vertical = 4.dp)
                                    .fillMaxSize(),
                                contentPadding = innerPadding
                            ) {
                                items(state.notes) { note ->

                                    val noteLabels =
                                        allLabels.filter { note.labelIds.contains(it.id) }
                                    val noteReminds =
                                        allReminds.filter { it.noteId == note.id }

                                    NoteCard(
                                        note = note,
                                        onClick = { onNoteClick(note) },
                                        onLongClick = { },
                                        labels = noteLabels,
                                        reminds = noteReminds
                                    )
                                }
                            }
                        }

                        is ListStyle.Grid -> {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 4.dp, vertical = 4.dp),
                                columns = GridCells.Adaptive(192.dp),
                                contentPadding = innerPadding
                            ) {
                                items(state.notes) { note ->
                                    val noteLabels =
                                        allLabels.filter { note.labelIds.contains(it.id) }
                                    val noteReminds =
                                        allReminds.filter { it.noteId == note.id }

                                    NoteCard(
                                        note = note,
                                        onClick = { onNoteClick(note) },
                                        onLongClick = {  },
                                        labels = noteLabels,
                                        reminds = noteReminds
                                    )
                                }
                            }
                        }
                    }
                }
            }
            else -> Unit
        }
    }
}