package com.hellguy39.hellnotes.search.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.components.NoteCard
import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: UiState,
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
                    TextField(
                        value = uiState.query,
                        onValueChange = { newText -> onQueryChanged(newText) },
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
                actions = {

                }
            )
        },
        content = { innerPadding ->
            Crossfade(targetState = uiState.listStyle) { style ->
                when(style) {
                    is ListStyle.Column -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .fillMaxSize(),
                            contentPadding = innerPadding
                        ) {
                            items(uiState.notes) { note ->
                                NoteCard(
                                    note = note,
                                    onClick = {  },
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
                            items(uiState.notes) { note ->
                                NoteCard(
                                    note = note,
                                    onClick = {  },
                                    onLongClick = {  },
                                )
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {},
    )
}