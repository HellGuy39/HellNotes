package com.hellguy39.hellnotes.feature.home.list.reminders

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.home.MainViewModel

fun NavGraphBuilder.remindersScreen(
    mainViewModel: MainViewModel
) {
    composable(
        route = GraphScreen.Main.Reminders.route,
    ) {
        RemindersRoute()
    }
}