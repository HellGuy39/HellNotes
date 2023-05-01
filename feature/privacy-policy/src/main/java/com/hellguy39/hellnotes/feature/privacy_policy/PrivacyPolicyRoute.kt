package com.hellguy39.hellnotes.feature.privacy_policy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun PrivacyPolicyRoute(
    navController: NavController,
    privacyPolicyViewModel: PrivacyPolicyViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

    val uiState by privacyPolicyViewModel.uiState.collectAsStateWithLifecycle()

    PrivacyPolicyScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        onTryAgain = {
            privacyPolicyViewModel.send(PrivacyPolicyUiEvent.TryAgain)
        }
    )
}