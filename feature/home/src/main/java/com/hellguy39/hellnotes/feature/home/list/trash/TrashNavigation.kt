package com.hellguy39.hellnotes.feature.home.list.trash

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.GraphScreen
import com.hellguy39.hellnotes.core.ui.values.Motions
import com.hellguy39.hellnotes.feature.home.MainViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.trashScreen(
    mainViewModel: MainViewModel
) {
    composable(
        route = GraphScreen.Main.Trash.route,
        enterTransition = { Motions().fadeEnter() },
        exitTransition = { Motions().fadeExit() },
        popEnterTransition = { Motions().fadePopEnter() },
        popExitTransition = { Motions().fadePopExit() },
    ) {
        TrashRoute()
    }
}