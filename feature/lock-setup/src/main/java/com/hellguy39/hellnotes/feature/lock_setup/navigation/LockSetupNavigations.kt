package com.hellguy39.hellnotes.feature.lock_setup.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.lock_setup.LockSetupRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.lockSetupScreen(
    navController: NavController
) {
    composable(
        route = "${Screen.LockSetup.route}/{${ArgumentKeys.LockType}}",
        arguments = listOf(
            navArgument(name = ArgumentKeys.LockType) {
                type = NavType.StringType
                //defaultValue = ArgumentDefaultValues.NewNote
            }
        ),
        enterTransition = {
            UiDefaults.Motion.ScreenEnterTransition
        },
        exitTransition = {
            UiDefaults.Motion.ScreenExitTransition
        },
        popEnterTransition = {
            UiDefaults.Motion.ScreenPopEnterTransition
        },
        popExitTransition = {
            UiDefaults.Motion.ScreenPopExitTransition
        },
    ) {
        LockSetupRoute(navController)
    }
}