package com.hellguy39.hellnotes.feature.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.model.Remind
import com.hellguy39.hellnotes.core.model.util.ListStyle
import com.hellguy39.hellnotes.core.ui.components.NoteSelection
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.search.components.SearchScreenContent
import com.hellguy39.hellnotes.feature.search.components.SearchTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: UiState,
    query: String,
    listStyle: ListStyle,
    noteSelection: NoteSelection,
    onQueryChanged: (query: String) -> Unit,
    allLabels: List<Label>,
    allReminds: List<Remind>
) {
    BackHandler(onBack = onNavigationButtonClick)

    val focusRequester = remember { FocusRequester() }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

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
                query = query,
                onQueryChanged = { newQuery -> onQueryChanged(newQuery) },
                focusRequester = focusRequester
            )
        },
        content = { innerPadding ->
            SearchScreenContent(
                uiState = uiState,
                innerPadding = innerPadding,
                listStyle = listStyle,
                allLabels = allLabels,
                allReminds = allReminds,
                noteSelection = noteSelection,
            )
        },
    )
}