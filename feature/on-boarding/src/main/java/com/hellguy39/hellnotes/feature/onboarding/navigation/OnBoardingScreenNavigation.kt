package com.hellguy39.hellnotes.feature.onboarding.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.feature.onboarding.OnBoardingRoute
import com.hellguy39.hellnotes.feature.onboarding.OnBoardingViewModel

fun NavGraphBuilder.onBoardingScreen(appState: HellNotesAppState) {
    composable(
        route = Screen.OnBoarding.route,
        arguments = listOf(),
        enterTransition = { null },
        popExitTransition = { null },
    ) { from ->
        val onBoardingViewModel: OnBoardingViewModel = hiltViewModel()

        OnBoardingRoute(
            onFinish = {
                onBoardingViewModel.saveOnBoardingState(completed = true)
                appState.navigateToHome(from)
            },
        )
    }
}
