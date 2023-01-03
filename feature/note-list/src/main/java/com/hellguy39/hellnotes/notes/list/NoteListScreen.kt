package com.hellguy39.hellnotes.notes.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.notes.*
import com.hellguy39.hellnotes.notes.list.components.*
import com.hellguy39.hellnotes.notes.list.events.NoteEvents
import com.hellguy39.hellnotes.notes.list.events.SortMenuEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarMenuEvents
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    isShowAppBarMenu: Boolean,
    isShowSortMenu: Boolean,
    noteEvents: NoteEvents,
    onFabAddClick:() -> Unit,
    onListStyleChange: () -> Unit,
    sortMenuEvents: SortMenuEvents,
    topAppBarEvents: TopAppBarEvents,
    topAppBarMenuEvents: TopAppBarMenuEvents,
    noteListUiState: NoteListUiState,
    labels: List<Label>,
    reminds: List<Remind>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                uiState = noteListUiState,
                scrollBehavior = scrollBehavior,
                isShowMenu = isShowAppBarMenu,
                topAppBarEvents = topAppBarEvents,
                topAppBarMenuEvents = topAppBarMenuEvents
            )
        },
        content = { innerPadding ->
            when(noteListUiState) {
                is NoteListUiState.Success -> {
                    Crossfade(targetState = noteListUiState.listStyle) { style ->
                        when(style) {
                            is ListStyle.Column -> {
                                NoteColumnList(
                                    innerPadding = innerPadding,
                                    uiState = noteListUiState,
                                    noteEvents = noteEvents,
                                    isShowSortMenu = isShowSortMenu,
                                    sortMenuEvents = sortMenuEvents,
                                    onListStyleChange = onListStyleChange,
                                    labels = labels,
                                    reminds = reminds
                                )
                            }
                            is ListStyle.Grid -> {
                                NoteGridList(
                                    innerPadding = innerPadding,
                                    uiState = noteListUiState,
                                    noteEvents = noteEvents,
                                    isShowSortMenu = isShowSortMenu,
                                    sortMenuEvents = sortMenuEvents,
                                    onListStyleChange = onListStyleChange
                                )
                            }
                        }
                    }
                }
                is NoteListUiState.Empty -> {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                }
                else -> Unit
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabAddClick() }
            ) {
                Icon(
                    painterResource(id = HellNotesIcons.Add),
                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.AddNote)
                )
            }
        },
    )
}
