package com.hellguy39.hellnotes.feature.label_edit.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.label_edit.LabelEditRoute

fun NavGraphBuilder.labelEditScreen(
    navController: NavController
) {
    composable(
        route = Screen.LabelEdit.withArgKeys(ArgumentKeys.Action),
        arguments = listOf(
            navArgument(name = ArgumentKeys.Action) {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        LabelEditRoute(navController = navController)
    }
}
