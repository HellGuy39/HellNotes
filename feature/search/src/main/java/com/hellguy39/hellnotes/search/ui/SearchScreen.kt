package com.hellguy39.hellnotes.search.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.components.NoteCard
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: UiState,
    query: String,
    listStyle: ListStyle,
    onNoteClick: (note: Note) -> Unit,
    onQueryChanged: (query: String) -> Unit
) {
    BackHandler(onBack = onNavigationButtonClick)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { newText -> onQueryChanged(newText) },
                        placeholder = {
                            Text(
                                text = stringResource(id = HellNotesStrings.Hint.Search),
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            errorBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        textStyle = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigationButtonClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.ArrowBack),
                            contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Back)
                        )
                    }
                },
                actions = {}
            )
        },
        content = { innerPadding ->
            Crossfade(targetState = uiState) { state ->
                when(state) {
                    is UiState.Success -> {
                        when(listStyle) {
                            is ListStyle.Column -> {
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 4.dp)
                                        .fillMaxSize(),
                                    contentPadding = innerPadding
                                ) {
                                    items(state.notes) { note ->
                                        NoteCard(
                                            note = note,
                                            onClick = { onNoteClick(note) },
                                            onLongClick = {  },
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
                                        NoteCard(
                                            note = note,
                                            onClick = { onNoteClick(note) },
                                            onLongClick = {  },
                                        )
                                    }
                                }
                            }
                        }
                    }
                    is UiState.Empty -> {
                        EmptyContentPlaceholder(
                            heroIcon = painterResource(id = HellNotesIcons.Search),
                            message = stringResource(id = HellNotesStrings.Text.NothingWasFound)
                        )
                    }
                    else -> Unit
                }
            }
        },
        floatingActionButton = {},
    )
}