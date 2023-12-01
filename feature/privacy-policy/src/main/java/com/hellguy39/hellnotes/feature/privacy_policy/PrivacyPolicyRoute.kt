package com.hellguy39.hellnotes.feature.privacy_policy

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun PrivacyPolicyRoute(
    navigateBack: () -> Unit,
    privacyPolicyViewModel: PrivacyPolicyViewModel = hiltViewModel()
) {
    BackHandler { navigateBack() }

    val uiState by privacyPolicyViewModel.uiState.collectAsStateWithLifecycle()

    PrivacyPolicyScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onTryAgain = {
            privacyPolicyViewModel.send(PrivacyPolicyUiEvent.TryAgain)
        }
    )
}