package com.hellguy39.hellnotes.feature.terms_and_conditions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun TermsAndConditionsRoute(
    navController: NavController,
    termsAndConditionsViewModel: TermsAndConditionsViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

    val uiState by termsAndConditionsViewModel.uiState.collectAsStateWithLifecycle()

    TermsAndConditionsScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        onTryAgain = {

        }
    )
}