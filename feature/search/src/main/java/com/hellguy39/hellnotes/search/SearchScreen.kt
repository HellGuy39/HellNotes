package com.hellguy39.hellnotes.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.model.Label
import com.hellguy39.hellnotes.model.Note
import com.hellguy39.hellnotes.model.Remind
import com.hellguy39.hellnotes.model.util.ListStyle
import com.hellguy39.hellnotes.search.components.SearchScreenContent
import com.hellguy39.hellnotes.search.components.SearchTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: UiState,
    query: String,
    listStyle: ListStyle,
    onNoteClick: (note: Note) -> Unit,
    onQueryChanged: (query: String) -> Unit,
    allLabels: List<Label>,
    allReminds: List<Remind>
) {
    BackHandler(onBack = onNavigationButtonClick)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchTopAppBar(
                onNavigationButtonClick = onNavigationButtonClick,
                scrollBehavior = scrollBehavior,
                query = query,
                onQueryChanged = { newQuery -> onQueryChanged(newQuery) }
            )
        },
        content = { innerPadding ->
            SearchScreenContent(
                uiState = uiState,
                innerPadding = innerPadding,
                listStyle = listStyle,
                allLabels = allLabels,
                allReminds = allReminds,
                onNoteClick = onNoteClick
            )
        },
    )
}