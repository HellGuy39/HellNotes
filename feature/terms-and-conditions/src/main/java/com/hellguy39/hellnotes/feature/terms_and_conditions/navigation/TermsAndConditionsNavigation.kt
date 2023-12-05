package com.hellguy39.hellnotes.feature.terms_and_conditions.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.values.AnimDuration
import com.hellguy39.hellnotes.feature.terms_and_conditions.TermsAndConditionsRoute

fun NavGraphBuilder.termsAndConditionsScreen(
    appState: HellNotesAppState
) {
    composable(
        route = Screen.TermsAndConditions.route,
        arguments = listOf(),
        enterTransition = {
            when(initialState.destination.route) {
                Screen.AboutApp.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                        animationSpec = tween(AnimDuration.medium)
                    )
                }
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when(targetState.destination.route) {
                Screen.AboutApp.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                        animationSpec = tween(AnimDuration.fast)
                    )
                }
                else -> null
            }
        }
    ) {
        TermsAndConditionsRoute(
            navigateBack = { appState.navigateUp() }
        )
    }
}
