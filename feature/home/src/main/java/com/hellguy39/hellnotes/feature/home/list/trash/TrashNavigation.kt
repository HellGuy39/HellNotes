package com.hellguy39.hellnotes.feature.home.list.trash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.home.MainViewModel

fun NavGraphBuilder.trashScreen(
    mainViewModel: MainViewModel
) {
    composable(
        route = GraphScreen.Main.Trash.route,
//        enterTransition = { Motions.fadeEnter() },
//        exitTransition = { Motions.fadeExit() },
//        popEnterTransition = { Motions.fadePopEnter() },
//        popExitTransition = { Motions.fadePopExit() },
    ) {
        TrashRoute()
    }
}