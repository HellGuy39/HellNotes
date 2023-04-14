package com.hellguy39.hellnotes.feature.changelog

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun ChangelogRoute(navController: NavController) {
    BackHandler(onBack = navController::popBackStack)

    ChangelogScreen(
        onNavigationButtonClick = navController::popBackStack
    )
}