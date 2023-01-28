package com.hellguy39.hellnotes.feature.home.reminders

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.components.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.components.NoteColumnList
import com.hellguy39.hellnotes.core.ui.components.NoteGridList
import com.hellguy39.hellnotes.core.ui.components.NoteSelection
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.reminders.components.ReminderTopAppBarSelection
import com.hellguy39.hellnotes.feature.home.reminders.components.RemindersTopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RemindersScreen(
    uiState: RemindersUiState,
    noteSelection: NoteSelection,
    reminderTopAppBarSelection: ReminderTopAppBarSelection
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RemindersTopAppBar(
                scrollBehavior = scrollBehavior,
                selection = reminderTopAppBarSelection
            )
        },
        content = { paddingValues ->

            if (uiState.notes.isEmpty()) {
                EmptyContentPlaceholder(
                    heroIcon = painterResource(id = HellNotesIcons.Notifications),
                    message = stringResource(id = HellNotesStrings.Text.Empty)
                )
                return@Scaffold
            }

            AnimatedContent(targetState = reminderTopAppBarSelection.listStyle) { listStyle ->
                when (listStyle) {
                    ListStyle.Column -> {
                        NoteColumnList(
                            innerPadding = paddingValues,
                            noteSelection = noteSelection,
                            pinnedNotes = listOf(),
                            unpinnedNotes = uiState.notes,
                            selectedNotes = uiState.selectedNotes,
                            listHeader = {
                                Text(
                                    text = stringResource(id = HellNotesStrings.Label.Upcoming),
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        )
                    }
                    ListStyle.Grid -> {
                        NoteGridList(
                            innerPadding = paddingValues,
                            noteSelection = noteSelection,
                            pinnedNotes = listOf(),
                            unpinnedNotes = uiState.notes,
                            selectedNotes = uiState.selectedNotes,
                            listHeader = {
                                Text(
                                    text = stringResource(id = HellNotesStrings.Label.Upcoming),
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}