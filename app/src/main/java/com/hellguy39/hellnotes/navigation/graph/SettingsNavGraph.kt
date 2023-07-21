package com.hellguy39.hellnotes.navigation.graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.value.Motions
import com.hellguy39.hellnotes.navigation.host.SettingsNavHost

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.settingsNavGraph(
    globalNavController: NavController,
) {
    composable(
        route = GraphScreen.Global.Settings.route,
        enterTransition = { Motions.fadeEnter() },
        exitTransition = { Motions.fadeExit() },
        popEnterTransition = { Motions.fadePopEnter() },
        popExitTransition = { Motions.fadePopExit() },
    ) {
        SettingsNavHost(globalNavController = globalNavController)
    }
}