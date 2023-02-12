package com.hellguy39.hellnotes.feature.language_selection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder

@Composable
fun LanguageSelectionRoute(
    navController: NavController,
    languageSelectionViewModel: LanguageSelectionViewModel = hiltViewModel(),
) {
    val uiState by languageSelectionViewModel.uiState.collectAsStateWithLifecycle()

    LanguageSelectionScreen(
        onNavigationBack = {
            navController.popBackStack()
        },
        onLanguageSelected = { code ->
            languageSelectionViewModel.setLanguageCode(code)
        },
        uiState = uiState
    )
}