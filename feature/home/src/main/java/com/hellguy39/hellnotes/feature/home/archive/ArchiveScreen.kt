package com.hellguy39.hellnotes.feature.home.archive

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBar
import com.hellguy39.hellnotes.feature.home.archive.components.ArchiveTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    uiState: ArchiveUiState,
    listStyle: ListStyle,
    noteSelection: NoteSelection,
    archiveTopAppBarSelection: ArchiveTopAppBarSelection,
    categories: List<NoteCategory>
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArchiveTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = archiveTopAppBarSelection
            )
        },
        content = { paddingValues ->
            NoteList(
                innerPadding = paddingValues,
                noteSelection = noteSelection,
                categories = categories,
                selectedNotes = uiState.selectedNotes,
                listStyle = listStyle,
                placeholder = {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.Archive),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                }
            )
        }
    )
}