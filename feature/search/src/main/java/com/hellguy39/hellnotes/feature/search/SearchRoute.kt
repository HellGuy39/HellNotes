package com.hellguy39.hellnotes.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail

@Composable
fun SearchRoute(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val noteStyle by searchViewModel.noteStyle.collectAsStateWithLifecycle()
    val listStyle by searchViewModel.listStyle.collectAsStateWithLifecycle()

    SearchScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        listStyle = listStyle,
        searchScreenSelection = SearchScreenSelection(
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
            }
        ),
        noteSelection = NoteSelection(
            noteStyle = noteStyle,
            onClick = { note ->
                navController.navigateToNoteDetail(noteId = note.id)
            },
            onLongClick = { note -> },
            onDismiss = { dismissDirection, note ->
                false
            },
            isSwipeable = false
        ),
        categories = listOf(
            NoteCategory(
                notes = uiState.notes
            ),
        )
    )
}