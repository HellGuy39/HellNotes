package com.hellguy39.hellnotes.feature.lock.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.core.ui.model.navigateToHome
import com.hellguy39.hellnotes.feature.lock.LockRoute

fun NavGraphBuilder.lockScreen(
    navController: NavController
) {
    composable(
        route = Screen.Lock.route,
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