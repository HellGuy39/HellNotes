package com.hellguy39.hellnotes.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.DateHelper
import com.hellguy39.hellnotes.core.ui.components.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail

@Composable
fun SearchRoute(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    dateHelper: DateHelper = searchViewModel.dateHelper
) {
    val haptic = LocalHapticFeedback.current

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
        noteSelection = NoteSelection(
            dateHelper = dateHelper,
            onClick = { note ->
                navController.navigateToNoteDetail(noteId = note.id ?: -1)
            },
            onLongClick = { note ->
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        ),
        allLabels = allLabels,
        allReminds = allReminds
    )
}