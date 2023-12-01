package com.hellguy39.hellnotes.feature.changelog

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun ChangelogRoute(
    changelogViewModel: ChangelogViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    BackHandler { navigateBack() }

    val uiState by changelogViewModel.uiState.collectAsStateWithLifecycle()

    ChangelogScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onTryAgain = {
            changelogViewModel.send(ChangelogUiEvent.TryAgain)
        }
    )
}