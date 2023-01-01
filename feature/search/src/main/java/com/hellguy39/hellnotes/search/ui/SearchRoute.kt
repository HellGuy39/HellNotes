package com.hellguy39.hellnotes.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.INavigations

@Composable
fun SearchRoute(
    navController: NavController,
    navigations: INavigations,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by searchViewModel.uiState.collectAsState()

    NoteListScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        uiState = uiState,
        onQueryChanged = { searchViewModel.updateSearchQuery(it) }
    )
}