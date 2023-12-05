package com.hellguy39.hellnotes.feature.label_edit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.label_edit.LabelEditRoute

fun NavGraphBuilder.labelEditScreen(
    appState: HellNotesAppState
) {
    composable(
        route = Screen.LabelEdit.withArgKeys(ArgumentKeys.Action),
        arguments = listOf(
            navArgument(name = ArgumentKeys.Action) {
                type = NavType.StringType
            }
        ),
        enterTransition = { null },
        popExitTransition = { null }
    ) {
        LabelEditRoute(
            navigateBack = { appState.navigateUp() }
        )
    }
}
