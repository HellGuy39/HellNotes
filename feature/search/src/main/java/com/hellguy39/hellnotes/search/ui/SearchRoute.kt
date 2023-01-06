package com.hellguy39.hellnotes.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.navigations.INavigations

@Composable
fun SearchRoute(
    navController: NavController,
    navigations: INavigations,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by searchViewModel.uiState.collectAsState()
    val listStyle by searchViewModel.listStyle.collectAsState()
    val query by searchViewModel.query.collectAsState()
    val allReminds by searchViewModel.reminds.collectAsState()
    val allLabels by searchViewModel.labels.collectAsState()

    SearchScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        uiState = uiState,
        query = query,
        listStyle = listStyle,
        onQueryChanged = { newQuery -> searchViewModel.updateSearchQuery(newQuery) },
        onNoteClick = { note ->
            navigations.navigateToNoteDetail(noteId = note.id ?: -1)
        },
        allLabels = allLabels,
        allReminds = allReminds
    )
}