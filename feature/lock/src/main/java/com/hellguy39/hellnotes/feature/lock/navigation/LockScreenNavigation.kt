package com.hellguy39.hellnotes.feature.lock.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.feature.lock.LockRoute

fun NavGraphBuilder.lockScreen(
    appState: HellNotesAppState
) {
    composable(
        route = Screen.Lock.route,
        enterTransition = { null },
        popExitTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null }
    ) { from ->
        LockRoute(
            onUnlock = {
                appState.navigateToHome(
                    from,
                    navOptions {
                        popUpTo(Screen.Lock.route) {
                            inclusive = true
                        }
                    }
                )
            },
        )
    }
}