package com.hellguy39.hellnotes.feature.labeledit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.labeledit.LabelEditRoute

fun NavGraphBuilder.labelEditScreen(appState: AppState) {
    composable(
        route = Screen.LabelEdit.withArgKeys(Arguments.Action.key),
        arguments =
            listOf(
                Arguments.Action.asNavigationArgument(),
            ),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) {
        LabelEditRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
