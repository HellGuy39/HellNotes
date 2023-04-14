package com.hellguy39.hellnotes.feature.terms_and_conditions

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun TermsAndConditionsRoute(navController: NavController) {
    BackHandler(onBack = navController::popBackStack)

    TermsAndConditionsScreen(
        onNavigationButtonClick = navController::popBackStack
    )
}