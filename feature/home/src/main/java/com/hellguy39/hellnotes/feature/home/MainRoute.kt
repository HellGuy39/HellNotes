package com.hellguy39.hellnotes.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.hellguy39.hellnotes.core.ui.layout.ListDetail
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.feature.home.edit.NoteEditRoute
import com.hellguy39.hellnotes.feature.home.list.archive.NotesListRoute

@Composable
fun MainRoute(
    mainNavController: NavHostController,
    isDetailOpen: Boolean,
    openedNoteId: Long,
    mainViewModel: MainViewModel,
    contentType: HNContentType,
    displayFeatures: List<DisplayFeature>,
    windowWidthSize: WindowWidthSizeClass,
    onDrawerOpen: (Boolean) -> Unit
) {
    ListDetail(
        modifier = Modifier.fillMaxSize(),
        isDetailOpen = isDetailOpen,
        onCloseDetail = { mainViewModel.onEvent(MainUiEvent.CloseNoteEditing) },
        contentType = contentType,
        detailKey = openedNoteId,
        list = { isDetailVisible ->
            NotesListRoute(
                mainNavController = mainNavController,
                mainViewModel = mainViewModel,
                onDrawerOpen = onDrawerOpen,
                windowWidthSize = windowWidthSize
            )
        },
        detail = { isListVisible ->
            NoteEditRoute(
                contentType = contentType,
                openedNoteId = openedNoteId,
                onCloseNoteEdit = { mainViewModel.onEvent(MainUiEvent.CloseNoteEditing) },
                windowWidthSize = windowWidthSize
            )
        },
        twoPaneStrategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 0.dp),
        displayFeatures = displayFeatures
    )
}