package com.hellguy39.hellnotes.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.navigation.host.SettingsNavHost

fun NavGraphBuilder.settingsNavGraph(
    displayFeatures: List<DisplayFeature>,
    globalNavController: NavController,
) {
    composable(
        route = GraphScreen.Global.Settings.route,
    ) {
        SettingsNavHost(
            displayFeatures = displayFeatures,
            navigateBack = { globalNavController.popBackStack() }
        )
    }
}