package com.hellguy39.hellnotes.feature.reset

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun ResetRoute(
    resetViewModel: ResetViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    TrackScreenView(screenName = "ResetScreen")

    BackHandler { navigateBack() }

    val uiState by resetViewModel.uiState.collectAsStateWithLifecycle()

    ResetScreen(
        uiState = uiState,
        onNavigationButtonClick = navigateBack,
        onResetClick = {
            resetViewModel.send(ResetUiEvent.Reset)
            navigateBack()
        },
        onToggleResetDatabase = {
            resetViewModel.send(ResetUiEvent.ToggleIsResetDatabase)
        },
        onToggleResetSettings = {
            resetViewModel.send(ResetUiEvent.ToggleIsResetSettings)
        },
    )
}
