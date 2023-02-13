package com.hellguy39.hellnotes.feature.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
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
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.search.components.SearchTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigationButtonClick: () -> Unit,
    uiState: SearchUiState,
    listStyle: ListStyle,
    noteSelection: NoteSelection,
    onQueryChanged: (query: String) -> Unit,
    categories: List<NoteCategory>
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
                query = uiState.search,
                onQueryChanged = { newQuery -> onQueryChanged(newQuery) },
                focusRequester = focusRequester
            )
        },
        content = { innerPadding ->
            Crossfade(targetState = categories) { categories ->
                NoteList(
                    innerPadding = innerPadding,
                    noteSelection = noteSelection,
                    categories = categories,
                    listStyle = listStyle,
                    placeholder = {
                        EmptyContentPlaceholder(
                            paddingValues = innerPadding,
                            heroIcon = painterResource(id = HellNotesIcons.Search),
                            message = stringResource(id = HellNotesStrings.Text.NothingWasFound)
                        )
                    }
                )
            }
        },
    )
}