package com.hellguy39.hellnotes.feature.languageselection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun LanguageSelectionRoute(
    languageSelectionViewModel: LanguageSelectionViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    TrackScreenView(screenName = "LanguageSelectionScreen")

    val uiState by languageSelectionViewModel.uiState.collectAsStateWithLifecycle()

    LanguageSelectionScreen(
        onNavigationButtonClick = { navigateBack() },
        onLanguageClick = { language -> languageSelectionViewModel.setLanguage(language) },
        uiState = uiState,
    )
}
