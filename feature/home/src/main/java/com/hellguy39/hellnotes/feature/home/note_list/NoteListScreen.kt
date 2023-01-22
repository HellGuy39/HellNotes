package com.hellguy39.hellnotes.feature.home.note_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.Note
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.NoteColumnList
import com.hellguy39.hellnotes.core.ui.components.NoteGridList
import com.hellguy39.hellnotes.core.ui.components.NoteSelection
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.HomeNoteListUiState
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBar
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteListScreen(
    onFabAddClick:() -> Unit,
    noteListTopAppBarSelection: NoteListTopAppBarSelection,
    noteListUiState: HomeNoteListUiState,
    selectedNotes: List<Note>,
    noteSelection: NoteSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)//pinnedScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                scrollBehavior = scrollBehavior,
                selectedNotes = selectedNotes,
                selection = noteListTopAppBarSelection
            )
        },
        content = { innerPadding ->
            AnimatedContent(targetState = noteListUiState) { uiState ->

                if (uiState.unpinnedNotes.isEmpty() && uiState.pinnedNotes.isEmpty()) {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                    return@AnimatedContent
                }

                when(uiState.listStyle) {
                    ListStyle.Column -> {
                        NoteColumnList(
                            innerPadding = innerPadding,
                            noteSelection = noteSelection,
                            pinnedNotes = uiState.pinnedNotes,
                            unpinnedNotes = uiState.unpinnedNotes,
                            selectedNotes = selectedNotes
                        )
                    }
                    ListStyle.Grid -> {
                        NoteGridList(
                            innerPadding = innerPadding,
                            noteSelection = noteSelection,
                            pinnedNotes = uiState.pinnedNotes,
                            unpinnedNotes = uiState.unpinnedNotes,
                            selectedNotes = selectedNotes
                        )
                    }
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