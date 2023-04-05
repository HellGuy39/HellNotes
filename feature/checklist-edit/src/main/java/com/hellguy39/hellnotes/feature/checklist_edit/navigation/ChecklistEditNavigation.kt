package com.hellguy39.hellnotes.feature.checklist_edit.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.checklist_edit.ChecklistEditRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.checklistEditScreen(
    navController: NavController
) {
    composable(
        route = Screen.ChecklistEdit.withArgKeys(ArgumentKeys.ChecklistId, ArgumentKeys.NoteId),
        arguments = listOf(
            navArgument(name = ArgumentKeys.ChecklistId) {
                type = NavType.LongType
                defaultValue = ArgumentDefaultValues.NewChecklist
            },
            navArgument(name = ArgumentKeys.NoteId) {
                type = NavType.LongType
            }
        ),
        enterTransition = {
            UiDefaults.Motion.ScreenEnterTransition
        },
        exitTransition = {
            UiDefaults.Motion.ScreenExitTransition
        },
        popEnterTransition = {
            UiDefaults.Motion.ScreenPopEnterTransition
        },
        popExitTransition = {
            UiDefaults.Motion.ScreenPopExitTransition
        },
    ) {
        ChecklistEditRoute(navController = navController)
    }
}
