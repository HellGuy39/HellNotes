package com.hellguy39.hellnotes.feature.home.list.archive

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.home.MainViewModel

fun NavGraphBuilder.archiveScreen(mainViewModel: MainViewModel) {
    composable(
        route = GraphScreen.Main.Archive.route,
    ) {
        ArchiveRoute()
    }
}
