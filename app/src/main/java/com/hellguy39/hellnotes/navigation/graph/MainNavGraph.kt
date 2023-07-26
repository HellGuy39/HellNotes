package com.hellguy39.hellnotes.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.navigation.host.MainNavHost

fun NavGraphBuilder.mainNavGraph(
    globalNavController: NavController,
) {
    composable(
        route = GraphScreen.Global.Main.route,
    ) {
        MainNavHost(globalNavController = globalNavController)
    }
}