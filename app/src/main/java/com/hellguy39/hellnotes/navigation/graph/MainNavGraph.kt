package com.hellguy39.hellnotes.navigation.graph

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.navigation.host.MainNavHost

fun NavGraphBuilder.mainNavGraph(
    globalNavController: NavController,
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>
) {
    composable(
        route = GraphScreen.Global.Main.route,
    ) {
        MainNavHost(
            displayFeatures = displayFeatures,
            windowSize = windowSize,
            navigateToSettings = {
                globalNavController.navigate(GraphScreen.Global.Settings.route)
            },
            navigateToAbout = {
                globalNavController.navigate(GraphScreen.Global.About.route)
            }
        )
    }
}