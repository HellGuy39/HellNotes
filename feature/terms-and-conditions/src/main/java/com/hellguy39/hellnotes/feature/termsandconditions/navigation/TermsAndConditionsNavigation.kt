package com.hellguy39.hellnotes.feature.termsandconditions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.termsandconditions.TermsAndConditionsRoute

fun NavGraphBuilder.termsAndConditionsScreen(appState: AppState) {
    composable(
        route = Screen.TermsAndConditions.route,
        arguments = listOf(),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.AboutApp.route -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.AboutApp.route -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) {
        TermsAndConditionsRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
