package com.hellguy39.hellnotes.feature.home.label

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
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBar
import com.hellguy39.hellnotes.feature.home.label.components.LabelTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelScreen(
    uiState: LabelUiState,
    listStyle: ListStyle,
    noteSelection: NoteSelection,
    labelTopAppBarSelection: LabelTopAppBarSelection,
    categories: List<NoteCategory>
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { paddingValues ->
            NoteList(
                innerPadding = paddingValues,
                noteSelection = noteSelection,
                categories = categories,
                selectedNotes = uiState.selectedNotes,
                listStyle = listStyle,
                placeholder = {
                    EmptyContentPlaceholder(
                        heroIcon = painterResource(id = HellNotesIcons.Label),
                        message = stringResource(id = HellNotesStrings.Text.Empty)
                    )
                }
            )
        },
        topBar = {
            LabelTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = labelTopAppBarSelection,
                label = uiState.label
            )
        }
    )
}