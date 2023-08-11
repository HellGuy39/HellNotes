package com.hellguy39.hellnotes.feature.label_selection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.ui.model.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.label_selection.LabelSelectionRoute

fun NavGraphBuilder.labelSelectionScreen(
    navController: NavController
) {
    composable(
        route = Screen.LabelSelection.withArgKeys(ArgumentKeys.NoteId),
        arguments = listOf(
            navArgument(name = ArgumentKeys.NoteId) {
                type = NavType.LongType
                defaultValue = ArgumentDefaultValues.NewNote
            }
        )
    ) {
        LabelSelectionRoute(navController = navController)
    }
}
