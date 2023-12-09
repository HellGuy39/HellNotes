package com.hellguy39.hellnotes.feature.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRoute(
    navigateBack: () -> Unit,
    navigateToNoteDetail: (id: Long) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        onNavigationButtonClick = { navigateBack() },
        uiState = uiState,
        listStyle = uiState.listStyle,
        searchScreenSelection =
            SearchScreenSelection(
                onQueryChanged = { search ->
                    searchViewModel.send(SearchScreenUiEvent.OnSearchChange(search))
                },
                onClearQuery = {
                    searchViewModel.send(SearchScreenUiEvent.OnClearSearch)
                },
                onUpdateArchiveFilter = { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleArchiveFilter(enabled))
                },
                onUpdateChecklistFilter = { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleChecklistFilter(enabled))
                },
                onUpdateReminderFilter = { enabled ->
                    searchViewModel.send(SearchScreenUiEvent.OnToggleReminderFilter(enabled))
                },
            ),
        noteSelection =
            NoteSelection(
                noteStyle = uiState.noteStyle,
                onClick = { note ->
                    navigateToNoteDetail(note.id ?: return@NoteSelection)
                },
                onLongClick = { note -> },
                onDismiss = { dismissDirection, note ->
                    false
                },
                isSwipeable = false,
            ),
        categories =
            listOf(
                NoteCategory(
                    notes = uiState.notes,
                ),
            ),
    )
}
