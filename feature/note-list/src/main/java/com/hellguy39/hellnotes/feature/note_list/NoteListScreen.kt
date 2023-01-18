package com.hellguy39.hellnotes.feature.note_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.notes.list.components.NoteColumnList
import com.hellguy39.hellnotes.feature.note_list.components.NoteGridList
import com.hellguy39.hellnotes.notes.list.components.NoteListTopAppBar
import com.hellguy39.hellnotes.notes.list.events.NoteEvents
import com.hellguy39.hellnotes.notes.list.events.SortMenuEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarEvents
import com.hellguy39.hellnotes.notes.list.events.TopAppBarMenuEvents
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.note_list.NoteListUiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
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
    reminds: List<Remind>,
    selectedNotes: List<Note>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                scrollBehavior = scrollBehavior,
                isShowMenu = isShowAppBarMenu,
                topAppBarEvents = topAppBarEvents,
                topAppBarMenuEvents = topAppBarMenuEvents,
                selectedNotes = selectedNotes
            )
        },
        content = { innerPadding ->
            AnimatedContent(targetState = noteListUiState) { uiState ->
                when(uiState) {
                    is NoteListUiState.Success -> {

                        if (uiState.notes.isEmpty() && uiState.pinnedNotes.isEmpty()) {
                            EmptyContentPlaceholder(
                                heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                                message = stringResource(id = HellNotesStrings.Text.Empty)
                            )
                            return@AnimatedContent
                        }

                        when(uiState.listStyle) {
                            is ListStyle.Column -> {
                                NoteColumnList(
                                    innerPadding = innerPadding,
                                    uiState = uiState,
                                    noteEvents = noteEvents,
                                    isShowSortMenu = isShowSortMenu,
                                    sortMenuEvents = sortMenuEvents,
                                    onListStyleChange = onListStyleChange,
                                    labels = labels,
                                    reminds = reminds,
                                    selectedNotes = selectedNotes
                                )
                            }
                            is ListStyle.Grid -> {
                                NoteGridList(
                                    innerPadding = innerPadding,
                                    uiState = uiState,
                                    noteEvents = noteEvents,
                                    isShowSortMenu = isShowSortMenu,
                                    sortMenuEvents = sortMenuEvents,
                                    onListStyleChange = onListStyleChange,
                                    labels = labels,
                                    reminds = reminds,
                                    selectedNotes = selectedNotes
                                )
                            }
                        }
                    }
                    is NoteListUiState.Loading -> Unit
                    else -> Unit
                }
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
