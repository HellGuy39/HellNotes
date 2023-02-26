package com.hellguy39.hellnotes.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.components.cards.NoteSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@Composable
fun SearchRoute(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val noteStyle by searchViewModel.noteStyle.collectAsStateWithLifecycle()
    val listStyle by searchViewModel.listStyle.collectAsStateWithLifecycle()

    SearchScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        uiState = uiState,
        listStyle = listStyle,
        onQueryChanged = { newQuery -> searchViewModel.updateSearchQuery(newQuery) },
        noteSelection = NoteSelection(
            noteStyle = noteStyle,
            onClick = { note ->
                navController.navigateToNoteDetail(noteId = note.id)
            },
            onLongClick = { note -> },
            onDismiss = { dismissDirection, note ->
                false
            }
        ),
        categories = listOf(
            NoteCategory(
                notes = uiState.notes
            ),
            NoteCategory(
                title = stringResource(id = HellNotesStrings.Label.Archived),
                notes = uiState.archivedNotes
            )
        )
    )
}