package com.hellguy39.hellnotes.feature.lock_setup.navigation

import androidx.compose.animation.*
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.lock_setup.LockSetupRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.lockSetupScreen(
    navController: NavController
) {
    composable(
        route = Screen.LockSetup.withArgKeys(ArgumentKeys.LockType),
        arguments = listOf(
            navArgument(name = ArgumentKeys.LockType) {
                type = NavType.StringType
            }
        ),
    ) {
        LockSetupRoute(navController)
    }
}