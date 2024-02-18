package com.hellguy39.hellnotes.feature.termsandconditions

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun TermsAndConditionsRoute(
    navigateBack: () -> Unit,
    termsAndConditionsViewModel: TermsAndConditionsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "TermsAndConditionsScreen")

    BackHandler { navigateBack() }

    val uiState by termsAndConditionsViewModel.uiState.collectAsStateWithLifecycle()

    TermsAndConditionsScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onTryAgain = {},
    )
}
