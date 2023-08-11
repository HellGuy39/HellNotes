package com.hellguy39.hellnotes.feature.language_selection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.language_selection.LanguageSelectionRoute

fun NavGraphBuilder.languageSelectionScreen(
    navController: NavController
) {
    composable(
        route = Screen.LanguageSelection.route,
    ) {
        LanguageSelectionRoute(navController = navController)
    }
}
