package com.hellguy39.hellnotes.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.search.SearchRoute

fun NavGraphBuilder.searchScreen(appState: AppState) {
    composable(
        route = Screen.Search.route,
        enterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
    ) { from ->
        SearchRoute(
            navigateBack = { appState.navigateUp() },
            navigateToNoteDetail = { id -> appState.navigateToNoteDetail(from, id) },
        )
    }
}
