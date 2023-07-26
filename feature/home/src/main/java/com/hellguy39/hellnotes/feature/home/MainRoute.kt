package com.hellguy39.hellnotes.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.hellguy39.hellnotes.core.ui.layout.ListDetail
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.feature.home.edit.NoteEditRoute
import com.hellguy39.hellnotes.feature.home.list.archive.NotesListRoute
import com.hellguy39.hellnotes.feature.home.list.archive.archiveScreen
import com.hellguy39.hellnotes.feature.home.list.notes.notesScreen
import com.hellguy39.hellnotes.feature.home.list.reminders.remindersScreen
import com.hellguy39.hellnotes.feature.home.list.trash.trashScreen

@Composable
fun MainRoute(
    innerPadding: PaddingValues,
    mainNavController: NavHostController,
    isDetailOpen: Boolean,
    openedNoteId: Long,
    mainViewModel: MainViewModel,
    contentType: HNContentType,
    displayFeatures: List<DisplayFeature>,
    onCloseNoteEdit: () -> Unit
) {
    ListDetail(
        modifier = Modifier,
        isDetailOpen = isDetailOpen,
        onCloseDetail = onCloseNoteEdit,
        contentType = contentType,
        detailKey = openedNoteId,
        list = { isDetailVisible ->
            NotesListRoute(
                mainNavController = mainNavController,
                mainViewModel = mainViewModel
            )
        },
        detail = { isListVisible ->
            NoteEditRoute(
                contentType = contentType,
                noteId = openedNoteId,
                onCloseNoteEdit = onCloseNoteEdit
            )
        },
        twoPaneStrategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 0.dp),
        displayFeatures = displayFeatures
    )
}