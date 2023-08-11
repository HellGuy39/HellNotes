package com.hellguy39.hellnotes.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.search.SearchRoute

fun NavGraphBuilder.searchScreen(
    navController: NavController,
) {
    composable(
        route = Screen.Search.route,
    ) {
        SearchRoute(navController)
    }
}