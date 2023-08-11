package com.hellguy39.hellnotes.feature.backup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.backup.BackupRoute

fun NavGraphBuilder.backupScreen(
    navController: NavController
) {
    composable(
        route = Screen.Backup.route,
    ) {
        BackupRoute(navController = navController)
    }
}
