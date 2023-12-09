package com.hellguy39.hellnotes.feature.termsandconditions

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TermsAndConditionsRoute(
    navigateBack: () -> Unit,
    termsAndConditionsViewModel: TermsAndConditionsViewModel = hiltViewModel(),
) {
    BackHandler { navigateBack() }

    val uiState by termsAndConditionsViewModel.uiState.collectAsStateWithLifecycle()

    TermsAndConditionsScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onTryAgain = {},
    )
}
