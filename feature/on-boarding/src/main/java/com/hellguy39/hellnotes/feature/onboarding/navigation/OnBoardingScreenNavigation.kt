package com.hellguy39.hellnotes.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.onboarding.OnBoardingRoute

fun NavGraphBuilder.onBoardingScreen(appState: AppState) {
    composable(
        route = Screen.OnBoarding.route,
        arguments = listOf(),
        enterTransition = { null },
        exitTransition = { null },
        popExitTransition = { null },
        popEnterTransition = { null },
    ) { from ->
        OnBoardingRoute(
            onFinish = {
                appState.navigateToHome(from)
            },
        )
    }
}
