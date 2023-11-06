package com.hellguy39.hellnotes.feature.changelog

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun ChangelogRoute(
    navController: NavController,
    changelogViewModel: ChangelogViewModel = hiltViewModel()
) {
    BackHandler { navController.popBackStack() }

    val uiState by changelogViewModel.uiState.collectAsStateWithLifecycle()

    ChangelogScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        onTryAgain = {
            changelogViewModel.send(ChangelogUiEvent.TryAgain)
        }
    )
}