package com.hellguy39.hellnotes.feature.privacy_policy.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.privacy_policy.PrivacyPolicyRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.privacyPolicyScreen(
    navController: NavController
) {
    composable(
        route = Screen.PrivacyPolicy.route,
        arguments = listOf(),
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
        PrivacyPolicyRoute(navController = navController)
    }
}
