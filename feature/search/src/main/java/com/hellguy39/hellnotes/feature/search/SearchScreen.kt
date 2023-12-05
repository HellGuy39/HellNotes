package com.hellguy39.hellnotes.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.datastore.ListStyle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.search.components.SearchTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: SearchUiState,
    listStyle: ListStyle,
    noteSelection: NoteSelection,
    searchScreenSelection: SearchScreenSelection,
    categories: List<NoteCategory>
) {
    BackHandler { onNavigationButtonClick() }

    val focusRequester = remember { FocusRequester() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchTopAppBar(
                onNavigationButtonClick = onNavigationButtonClick,
                scrollBehavior = scrollBehavior,
                query = uiState.search,
                onQueryChanged = searchScreenSelection.onQueryChanged,
                focusRequester = focusRequester,
                onClearQuery = searchScreenSelection.onClearQuery
            )
        },
        content = { innerPadding ->
            Crossfade(
                targetState = categories,
                label = "search_screen_content"
            ) { categories ->

                if (uiState.notes.isEmpty() && !uiState.isLoading) {
                    EmptyContentPlaceholder(
                        modifier = Modifier
                            .padding(horizontal = Spaces.large)
                            .padding(innerPadding)
                            .fillMaxSize(),
                        heroIcon = painterResource(id = HellNotesIcons.Search),
                        message = stringResource(id = HellNotesStrings.Placeholder.NothingWasFound)
                    )
                }

                NoteList(
                    innerPadding = innerPadding,
                    noteSelection = noteSelection,
                    categories = categories,
                    listStyle = listStyle,
                    listHeader = {
                        Column {
                            LazyRow(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                item {
                                    FilterChip(
                                        modifier = Modifier.height(FilterChipDefaults.Height),
                                        selected = uiState.filters.withChecklist,
                                        onClick = {
                                            searchScreenSelection.onUpdateChecklistFilter(!uiState.filters.withChecklist)
                                        },
                                        label = {
                                            Text(text = stringResource(id = HellNotesStrings.Label.Checklist))
                                        },
                                        leadingIcon = {
                                            Icon(
                                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                                painter = painterResource(id = HellNotesIcons.Checklist),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                                item {
                                    FilterChip(
                                        modifier = Modifier.height(FilterChipDefaults.Height),
                                        selected = uiState.filters.withReminder,
                                        onClick = {
                                            searchScreenSelection.onUpdateReminderFilter(!uiState.filters.withReminder)
                                        },
                                        label = {
                                            Text(text = stringResource(id = HellNotesStrings.Label.Reminder))
                                        },
                                        leadingIcon = {
                                            Icon(
                                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                                painter = painterResource(id = HellNotesIcons.Alarm),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                                item {
                                    FilterChip(
                                        modifier = Modifier.height(FilterChipDefaults.Height),
                                        selected = uiState.filters.withArchive,
                                        onClick = {
                                            searchScreenSelection.onUpdateArchiveFilter(!uiState.filters.withArchive)
                                        },
                                        label = {
                                            Text(text = stringResource(id = HellNotesStrings.Label.Archive))
                                        },
                                        leadingIcon = {
                                            Icon(
                                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                                painter = painterResource(id = HellNotesIcons.Archive),
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                    .alpha(0.5f),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                )
            }

        }
    )
}

data class SearchScreenSelection(
    val onQueryChanged: (query: String) -> Unit,
    val onClearQuery: () -> Unit,
    val onUpdateReminderFilter: (Boolean) -> Unit,
    val onUpdateChecklistFilter: (Boolean) -> Unit,
    val onUpdateArchiveFilter: (Boolean) -> Unit,
)