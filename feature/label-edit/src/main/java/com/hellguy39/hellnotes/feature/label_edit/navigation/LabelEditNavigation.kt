package com.hellguy39.hellnotes.feature.label_edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.model.Screen
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
    ) {
        LabelEditRoute(navController = navController)
    }
}
