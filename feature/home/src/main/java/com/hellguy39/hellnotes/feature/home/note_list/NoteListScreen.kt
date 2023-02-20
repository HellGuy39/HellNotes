package com.hellguy39.hellnotes.feature.home.note_list

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.*
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.note_list.components.ListConfiguration
import com.hellguy39.hellnotes.feature.home.note_list.components.ListConfigurationSelection
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBar
import com.hellguy39.hellnotes.feature.home.note_list.components.NoteListTopAppBarSelection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NoteListScreen(
    onFabAddClick:() -> Unit,
    noteListTopAppBarSelection: NoteListTopAppBarSelection,
    listConfigurationSelection: ListConfigurationSelection,
    uiState: NoteListUiState,
    noteSelection: NoteSelection,
    snackbarHostState: SnackbarHostState,
    categories: List<NoteCategory>
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    val sortingMenuState = rememberDropdownMenuState()

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

            if (uiState.isLoading) {
                return@Scaffold
            }

            AnimatedContent(noteListTopAppBarSelection.listStyle) { listStyle ->
                NoteList(
                    innerPadding = innerPadding,
                    noteSelection = noteSelection,
                    categories = categories,
                    selectedNotes = uiState.selectedNotes,
                    listStyle = listStyle,
                    listHeader = {
                        ListConfiguration(
                            selection = listConfigurationSelection,
                            menuState = sortingMenuState
                        )
                    },
                    placeholder = {
                        EmptyContentPlaceholder(
                            paddingValues = innerPadding,
                            heroIcon = painterResource(id = HellNotesIcons.NoteAdd),
                            message = stringResource(id = HellNotesStrings.Text.Empty)
                        )
                    }
                )
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