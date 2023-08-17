package com.hellguy39.hellnotes.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.navigation.host.SettingsNavHost

fun NavGraphBuilder.settingsNavGraph(
    globalNavController: NavController,
) {
    composable(
        route = GraphScreen.Global.Settings.route,
    ) {
        SettingsNavHost(
            navigateBack = { globalNavController.popBackStack() }
        )
    }
}