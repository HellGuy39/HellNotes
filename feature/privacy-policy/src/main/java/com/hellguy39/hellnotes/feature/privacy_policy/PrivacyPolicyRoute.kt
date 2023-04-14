package com.hellguy39.hellnotes.feature.privacy_policy

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun PrivacyPolicyRoute(
    navController: NavController
) {
    BackHandler(onBack = navController::popBackStack)

    PrivacyPolicyScreen(
        onNavigationButtonClick = navController::popBackStack
    )
}