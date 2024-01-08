package com.hellguy39.hellnotes.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.hellguy39.hellnotes.core.ui.components.list.NoteList
import com.hellguy39.hellnotes.core.ui.components.placeholer.EmptyContentPlaceholder
import com.hellguy39.hellnotes.core.ui.focus.requestFocusWhenBeAvailable
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.search.components.SearchTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: SearchUiState,
    onClick: (Note) -> Unit,
    onLongClick: (Note) -> Unit,
    onQueryChanged: (query: String) -> Unit,
    onClearQuery: () -> Unit,
    onUpdateReminderFilter: (Boolean) -> Unit,
    onUpdateChecklistFilter: (Boolean) -> Unit,
    onUpdateArchiveFilter: (Boolean) -> Unit,
) {
    BackHandler { onNavigationButtonClick() }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val focusRequester = remember { FocusRequester() }

    focusRequester.requestFocusWhenBeAvailable()

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchTopAppBar(
                onNavigationButtonClick = onNavigationButtonClick,
                scrollBehavior = scrollBehavior,
                query = uiState.search,
                onQueryChanged = onQueryChanged,
                focusRequester = focusRequester,
                onClearQuery = onClearQuery,
            )
        },
        content = { innerPadding ->

            if (uiState.isEmpty) {
                EmptyContentPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    heroIcon = UiIcon.DrawableResources(AppIcons.Search),
                    message = UiText.StringResources(AppStrings.Placeholder.NothingWasFound),
                )
            }

            Crossfade(
                targetState = uiState.noteCategories,
                label = "search_screen_content",
            ) { categories ->
                NoteList(
                    innerPadding = innerPadding,
                    categories = categories,
                    listStyle = uiState.listStyle,
                    noteStyle = uiState.noteStyle,
                    onClick = onClick,
                    onLongClick = onLongClick,
                    listHeader = {
                        Column {
                            LazyRow(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                item {
                                    FilterChip(
                                        modifier = Modifier.height(FilterChipDefaults.Height),
                                        selected = uiState.filters.withChecklist,
                                        onClick = {
                                            onUpdateChecklistFilter(!uiState.filters.withChecklist)
                                        },
                                        label = {
                                            Text(text = stringResource(id = AppStrings.Label.Checklist))
                                        },
                                        leadingIcon = {
                                            Icon(
                                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                                painter = painterResource(id = AppIcons.Checklist),
                                                contentDescription = null,
                                            )
                                        },
                                    )
                                }
                                item {
                                    FilterChip(
                                        modifier = Modifier.height(FilterChipDefaults.Height),
                                        selected = uiState.filters.withReminder,
                                        onClick = {
                                            onUpdateReminderFilter(!uiState.filters.withReminder)
                                        },
                                        label = {
                                            Text(text = stringResource(id = AppStrings.Label.Reminder))
                                        },
                                        leadingIcon = {
                                            Icon(
                                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                                painter = painterResource(id = AppIcons.Alarm),
                                                contentDescription = null,
                                            )
                                        },
                                    )
                                }
                                item {
                                    FilterChip(
                                        modifier = Modifier.height(FilterChipDefaults.Height),
                                        selected = uiState.filters.withArchive,
                                        onClick = {
                                            onUpdateArchiveFilter(!uiState.filters.withArchive)
                                        },
                                        label = {
                                            Text(text = stringResource(id = AppStrings.Label.Archive))
                                        },
                                        leadingIcon = {
                                            Icon(
                                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                                painter = painterResource(id = AppIcons.Archive),
                                                contentDescription = null,
                                            )
                                        },
                                    )
                                }
                            }
                            Divider(
                                modifier =
                                    Modifier
                                        .padding(vertical = 4.dp, horizontal = 8.dp)
                                        .alpha(0.5f),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                            )
                        }
                    },
                )
            }
        },
    )
}
