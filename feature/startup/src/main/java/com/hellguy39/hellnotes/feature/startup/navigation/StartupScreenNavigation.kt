package com.hellguy39.hellnotes.feature.startup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLock
import com.hellguy39.hellnotes.core.ui.navigations.navigateToOnBoarding
import com.hellguy39.hellnotes.feature.startup.StartupRoute

fun NavGraphBuilder.startupScreen(
    appState: HellNotesAppState
) {
    composable(
        route = Screen.Startup.route,
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) { from ->
        StartupRoute(
            navigateToHome = {
                appState.navigateToHome(
                    from,
                    navOptions {
                        popUpTo(Screen.Startup.route) {
                            inclusive = true
                        }
                    }
                )
            },
            navigateToLock = {
                appState.navigateToLock(
                    from,
                    navOptions {
                        popUpTo(Screen.Startup.route) {
                            inclusive = true
                        }
                    }
                )
            },
            navigateToOnBoarding = {
                appState.navigateToOnBoarding(from)
            }
        )
    }
}