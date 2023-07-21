package com.hellguy39.hellnotes.feature.label_selection.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.label_selection.LabelSelectionRoute

@OptIn(ExperimentalAnimationApi::class)
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
