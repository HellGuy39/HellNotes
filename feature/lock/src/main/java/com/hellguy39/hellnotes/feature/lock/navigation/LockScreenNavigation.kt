package com.hellguy39.hellnotes.feature.lock.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.lock.LockRoute

fun NavGraphBuilder.lockScreen(appState: AppState) {
    composable(
        route = Screen.Lock.route,
        enterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
    ) { from ->
        val activity = LocalContext.current as FragmentActivity

        LockRoute(
            activity = activity,
            onUnlock = {
                appState.navigateToHome(
                    from,
                    navOptions {
                        popUpTo(Screen.Lock.route) {
                            inclusive = true
                        }
                    },
                )
            },
        )
    }
}
