package com.hellguy39.hellnotes.feature.home.list.reminders

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.home.MainViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.remindersScreen(mainViewModel: MainViewModel) {
    composable(
        route = GraphScreen.Main.Reminders.route,
//        enterTransition = { Motions.fadeEnter() },
//        exitTransition = { Motions.fadeExit() },
//        popEnterTransition = { Motions.fadePopEnter() },
//        popExitTransition = { Motions.fadePopExit() },
    ) {
        RemindersRoute()
    }
}