package com.hellguy39.hellnotes.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun SearchRoute(
    navigateBack: () -> Unit,
    navigateToNoteDetail: (id: Long) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "SearchScreen")

    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()

    // TODO: refactor
    SearchScreen(
        onNavigationButtonClick = remember { { navigateBack() } },
        uiState = uiState,
        onQueryChanged =
            remember {
                { search ->
                    searchViewModel.send(SearchScreenUiEvent.OnSearchChange(search))
                }
            },
        onClearQuery =
            remember {
                {
                    searchViewModel.send(SearchScreenUiEvent.OnClearSearch)
                }
            },
        onUpdateArchiveFilter =
            remember {
                { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleArchiveFilter(enabled))
                }
            },
        onUpdateChecklistFilter =
            remember {
                { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleChecklistFilter(enabled))
                }
            },
        onUpdateReminderFilter =
            remember {
                { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleReminderFilter(enabled))
                }
            },
        onClick =
            remember {
                { noteId ->
                    if (noteId != null) {
                        navigateToNoteDetail(noteId)
                    }
                }
            },
        onLongClick = remember { { note -> } },
    )
}
