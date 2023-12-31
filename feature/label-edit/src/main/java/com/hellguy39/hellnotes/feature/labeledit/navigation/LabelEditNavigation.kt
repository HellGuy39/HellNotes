package com.hellguy39.hellnotes.feature.labeledit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.labeledit.LabelEditRoute

fun NavGraphBuilder.labelEditScreen(appState: AppState) {
    composable(
        route = Screen.LabelEdit.withArgKeys(Arguments.Action.key),
        arguments =
            listOf(
                navArgument(name = Arguments.Action.key) {
                    type = NavType.StringType
                },
            ),
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) {
        LabelEditRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
