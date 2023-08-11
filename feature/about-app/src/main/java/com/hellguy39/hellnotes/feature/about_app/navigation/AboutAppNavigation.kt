package com.hellguy39.hellnotes.feature.about_app.navigation

import androidx.compose.animation.*
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen

fun NavGraphBuilder.aboutAppScreen(
    navController: NavController
) {
    composable(
        route = Screen.AboutApp.route,
    ) {
        //AboutAppRoute(navController = navController)
    }
}
