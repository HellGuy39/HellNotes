package com.hellguy39.hellnotes.navigation.graph

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.navigation.host.AboutNavHost

fun NavGraphBuilder.aboutNavGraph(
    displayFeatures: List<DisplayFeature>,
    windowSize: WindowSizeClass,
    globalNavController: NavController,
) {
    composable(
        route = GraphScreen.Global.About.route,
    ) {
        AboutNavHost(
            displayFeatures = displayFeatures,
            windowSize = windowSize
        )
    }
}