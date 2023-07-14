package com.hellguy39.hellnotes.feature.home.list.archive

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.GraphScreen
import com.hellguy39.hellnotes.core.ui.values.Motions
import com.hellguy39.hellnotes.feature.home.MainViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.archiveScreen(mainViewModel: MainViewModel) {
    composable(
        route = GraphScreen.Main.Archive.route,
        enterTransition = { Motions.fadeEnter() },
        exitTransition = { Motions.fadeExit() },
        popEnterTransition = { Motions.fadePopEnter() },
        popExitTransition = { Motions.fadePopExit() },
    ) {
        ArchiveRoute()
    }
}
