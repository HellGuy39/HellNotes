package com.hellguy39.hellnotes.feature.terms_and_conditions

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun TermsAndConditionsRoute(
    navController: NavController,
    termsAndConditionsViewModel: TermsAndConditionsViewModel = hiltViewModel()
) {
    BackHandler { navController.popBackStack() }

    val uiState by termsAndConditionsViewModel.uiState.collectAsStateWithLifecycle()

    TermsAndConditionsScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        onTryAgain = {

        }
    )
}