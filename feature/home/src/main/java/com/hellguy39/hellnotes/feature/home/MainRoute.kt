package com.hellguy39.hellnotes.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hellguy39.hellnotes.core.ui.layout.ListDetail
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.feature.home.edit.NoteEditRoute
import com.hellguy39.hellnotes.feature.home.list.archive.archiveScreen
import com.hellguy39.hellnotes.feature.home.list.labels.labelsScreen
import com.hellguy39.hellnotes.feature.home.list.notes.notesScreen
import com.hellguy39.hellnotes.feature.home.list.reminders.remindersScreen
import com.hellguy39.hellnotes.feature.home.list.trash.trashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainRoute(
    innerPadding: PaddingValues,
    mainNavController: NavHostController,
    mainViewModel: MainViewModel,
    contentType: HNContentType,
    displayFeatures: List<DisplayFeature>,
    onCloseNoteEdit: () -> Unit
) {
    val isDetailOpen by mainViewModel.isDetailOpen.collectAsStateWithLifecycle()
    val openedNoteId by mainViewModel.openedNoteId.collectAsStateWithLifecycle()

    ListDetail(
        modifier = Modifier,
        isDetailOpen = isDetailOpen,
        onCloseDetail = onCloseNoteEdit,
        contentType = contentType,
        detailKey = openedNoteId,
        list = { isDetailVisible ->
            AnimatedNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = mainNavController,
                startDestination = GraphScreen.Main.start().route
            ) {
                notesScreen(mainViewModel)

                remindersScreen(mainViewModel)

                archiveScreen(mainViewModel)

                labelsScreen(mainViewModel)

                trashScreen(mainViewModel)
            }
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