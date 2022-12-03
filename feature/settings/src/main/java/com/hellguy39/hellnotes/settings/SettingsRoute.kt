package com.hellguy39.hellnotes.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SettingsRoute(
    navController: NavController
) {
    SettingsScreen(
        onNavigationButtonClick = {
            navController.popBackStack()
        }
    )
}