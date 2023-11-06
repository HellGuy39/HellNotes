package com.hellguy39.hellnotes.feature.lock.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.feature.lock.LockRoute

fun NavGraphBuilder.lockScreen(
    navController: NavController
) {
    composable(
        route = Screen.Lock.route,
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        }
    ) {
        LockRoute(
            onUnlock = {
                navController.navigateToHome(
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