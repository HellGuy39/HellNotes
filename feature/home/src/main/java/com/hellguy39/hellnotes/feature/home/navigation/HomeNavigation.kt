package com.hellguy39.hellnotes.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToAboutApp
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSettings
import com.hellguy39.hellnotes.feature.home.HomeRoute

fun NavGraphBuilder.homeScreen(
    appState: HellNotesAppState,
) {
    composable(
        route = Screen.Home.route,
        arguments = listOf(),
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) { from ->
        HomeRoute(
            navigateToSettings = { appState.navigateToSettings(from) },
            navigateToAbout = { appState.navigateToAboutApp(from) },
            navigateToSearch = { appState.navigateToSearch(from) },
            navigateToNoteDetail = { id -> appState.navigateToNoteDetail(from, id) },
            navigateToLabelEdit = { action -> appState.navigateToLabelEdit(from, action) },
        )
    }
}
