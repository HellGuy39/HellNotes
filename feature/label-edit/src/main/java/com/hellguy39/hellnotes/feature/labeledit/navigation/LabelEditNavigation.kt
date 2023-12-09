package com.hellguy39.hellnotes.feature.labeledit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.labeledit.LabelEditRoute

fun NavGraphBuilder.labelEditScreen(appState: HellNotesAppState) {
    composable(
        route = Screen.LabelEdit.withArgKeys(ArgumentKeys.ACTION),
        arguments =
            listOf(
                navArgument(name = ArgumentKeys.ACTION) {
                    type = NavType.StringType
                },
            ),
        enterTransition = { null },
        popExitTransition = { null },
    ) {
        LabelEditRoute(
            navigateBack = { appState.navigateUp() },
        )
    }
}
