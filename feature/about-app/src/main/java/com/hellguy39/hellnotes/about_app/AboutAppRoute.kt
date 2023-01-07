package com.hellguy39.hellnotes.about_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AboutAppRoute(
    navController: NavController
) {
    AboutAppScreen(
        onNavigationButtonClick = { navController.popBackStack() }
    )
}