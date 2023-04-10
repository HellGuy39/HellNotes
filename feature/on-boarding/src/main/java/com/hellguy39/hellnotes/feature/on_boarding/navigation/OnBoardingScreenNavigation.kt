package com.hellguy39.hellnotes.feature.on_boarding.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.on_boarding.OnBoardingRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.onBoardingScreen(
    navController: NavController
) {
    composable(
        route = Screen.OnBoarding.route,
        arguments = listOf(),
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        OnBoardingRoute(
            onFinish = { navController.popBackStack() }
        )
    }
}
