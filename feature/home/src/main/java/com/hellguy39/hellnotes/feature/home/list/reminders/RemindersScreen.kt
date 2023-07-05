package com.hellguy39.hellnotes.feature.home.list.reminders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.model.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.snack.CustomSnackbarHost
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.list.reminders.components.ReminderTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.list.reminders.components.RemindersTopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RemindersScreen(
    uiState: RemindersUiState,
    appBarSelection: ReminderTopAppBarSelection,
    screenSelection: RemindersScreenSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RemindersTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = appBarSelection,
                selectedNoteWrappers = screenSelection.selectedNoteWrappers
            )
        },
        content = { paddingValues ->
            when(uiState) {
                is RemindersUiState.Idle -> Unit
                is RemindersUiState.Empty -> {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(paddingValues)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.Notifications),
                        message = stringResource(id = HellNotesStrings.Placeholder.Empty)
                    )
                }
                is RemindersUiState.Success -> {
                    NoteList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp),
                        innerPadding = paddingValues,
                        noteSelection = screenSelection.noteSelection,
                        categories = listOf(
                            NoteCategory(notes = uiState.noteWrappersWithReminders)
                        ),
                        listStyle = screenSelection.listStyle,
                        selectedNotes = screenSelection.selectedNoteWrappers,
                        listHeader = {
                            Text(
                                text = stringResource(id = HellNotesStrings.Label.Upcoming),
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    )
                }
            }
        },
        snackbarHost = { CustomSnackbarHost(state = screenSelection.snackbarHostState) },
    )
}

data class RemindersScreenSelection(
    val listStyle: ListStyle,
    val noteStyle: NoteStyle,
    val selectedNoteWrappers: List<NoteWrapper>,
    val noteSelection: NoteSelection,
    val snackbarHostState: SnackbarHostState
)