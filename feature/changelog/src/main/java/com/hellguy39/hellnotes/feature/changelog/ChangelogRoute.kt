package com.hellguy39.hellnotes.feature.changelog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun ChangelogRoute(
    navController: NavController,
    changelogViewModel: ChangelogViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

    val uiState by changelogViewModel.uiState.collectAsStateWithLifecycle()

    ChangelogScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        onTryAgain = {
            changelogViewModel.send(ChangelogUiEvent.TryAgain)
        }
    )
}