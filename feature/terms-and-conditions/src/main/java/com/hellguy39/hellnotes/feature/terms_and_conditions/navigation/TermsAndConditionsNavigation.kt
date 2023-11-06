package com.hellguy39.hellnotes.feature.terms_and_conditions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.terms_and_conditions.TermsAndConditionsRoute

fun NavGraphBuilder.termsAndConditionsScreen(
    navController: NavController
) {
    composable(
        route = Screen.TermsAndConditions.route,
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
        TermsAndConditionsRoute(navController = navController)
    }
}
