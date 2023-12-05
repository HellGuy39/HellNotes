package com.hellguy39.hellnotes.feature.terms_and_conditions

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun TermsAndConditionsRoute(
    navigateBack: () -> Unit,
    termsAndConditionsViewModel: TermsAndConditionsViewModel = hiltViewModel()
) {
    BackHandler { navigateBack() }

    val uiState by termsAndConditionsViewModel.uiState.collectAsStateWithLifecycle()

    TermsAndConditionsScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onTryAgain = {}
    )
}