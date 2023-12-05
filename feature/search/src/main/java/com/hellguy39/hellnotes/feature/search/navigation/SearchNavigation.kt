package com.hellguy39.hellnotes.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.feature.search.SearchRoute

fun NavGraphBuilder.searchScreen(
    appState: HellNotesAppState
) {
    composable(
        route = Screen.Search.route,
        enterTransition = { null },
        popExitTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null }
    ) { from ->
        SearchRoute(
            navigateBack = { appState.navigateUp() },
            navigateToNoteDetail = { id -> appState.navigateToNoteDetail(from, id) }
        )
    }
}