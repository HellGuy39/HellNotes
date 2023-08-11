package com.hellguy39.hellnotes.feature.on_boarding.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.core.ui.model.navigateToHome
import com.hellguy39.hellnotes.feature.on_boarding.OnBoardingRoute
import com.hellguy39.hellnotes.feature.on_boarding.OnBoardingViewModel

fun NavGraphBuilder.onBoardingScreen(
    navController: NavController
) {
    composable(
        route = Screen.OnBoarding.route,
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
