package com.hellguy39.hellnotes.feature.backup.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.backup.BackupRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.backupScreen(
    navController: NavController
) {
    composable(
        route = Screen.Backup.route,
        arguments = listOf(),
    ) {
        BackupRoute(navController = navController)
    }
}
