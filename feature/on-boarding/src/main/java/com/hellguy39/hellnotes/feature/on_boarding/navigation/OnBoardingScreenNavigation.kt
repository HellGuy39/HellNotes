package com.hellguy39.hellnotes.feature.on_boarding.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.feature.on_boarding.OnBoardingRoute
import com.hellguy39.hellnotes.feature.on_boarding.OnBoardingViewModel

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
        val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()

        OnBoardingRoute(
            onFinish = {
                onBoardingViewModel.saveOnBoardingState(completed = true)
                navController.navigateToHome(
                    navOptions {
                        popUpTo(Screen.Startup.route) {
                            inclusive = true
                        }
                    }
                )
            }
        )
    }
}
