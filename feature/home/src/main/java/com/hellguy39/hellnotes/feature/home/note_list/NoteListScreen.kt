package com.hellguy39.hellnotes.feature.home.note_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.note_list.components.ListConfiguration
import com.hellguy39.hellnotes.feature.home.note_list.components.ListConfigurationSelection
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBar
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onFabAddClick:() -> Unit,
    noteListTopAppBarSelection: NoteListTopAppBarSelection,
    listConfigurationSelection: ListConfigurationSelection,
    uiState: NoteListUiState,
    noteSelection: NoteSelection,
    snackbarHostState: SnackbarHostState
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    val sortingMenuState = rememberDialogState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteListTopAppBar(
                scrollBehavior = scrollBehavior,
                selectedNotes = uiState.selectedNotes,
                selection = noteListTopAppBarSelection
            )
        },
        content = { innerPadding ->

            if (uiState.unpinnedNotes.isEmpty() && uiState.pinnedNotes.isEmpty()) {
                EmptyContentPlaceholder(
                    heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                    message = stringResource(id = HellNotesStrings.Text.Empty)
                )
                return@Scaffold
            }

            when(noteListTopAppBarSelection.listStyle) {
                ListStyle.Column -> {
                    NoteColumnList(
                        innerPadding = innerPadding,
                        noteSelection = noteSelection,
                        pinnedNotes = uiState.pinnedNotes,
                        unpinnedNotes = uiState.unpinnedNotes,
                        selectedNotes = uiState.selectedNotes,
                        listHeader = {
                            ListConfiguration(
                                selection = listConfigurationSelection,
                                menuState = sortingMenuState
                            )
                        }
                    )
                }
                ListStyle.Grid -> {
                    NoteGridList(
                        innerPadding = innerPadding,
                        noteSelection = noteSelection,
                        pinnedNotes = uiState.pinnedNotes,
                        unpinnedNotes = uiState.unpinnedNotes,
                        selectedNotes = uiState.selectedNotes,
                        listHeader = {
                            ListConfiguration(
                                selection = listConfigurationSelection,
                                menuState = sortingMenuState
                            )
                        }
                    )
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
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(12.dp),
                    action = {
                        TextButton(
                            onClick = {
                                data.performAction()
                            },
                        ) {
                            Text(
                                text = data.visuals.actionLabel ?: "",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.inversePrimary
                                )
                            )
                        }
                    },
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    )
}