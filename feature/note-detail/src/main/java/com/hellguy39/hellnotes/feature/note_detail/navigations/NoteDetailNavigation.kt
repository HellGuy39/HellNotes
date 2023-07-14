package com.hellguy39.hellnotes.feature.note_detail.navigations

import androidx.compose.animation.*
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.navigations.Screen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteDetailScreen(
    navController: NavController
) {
    composable(
        route = Screen.NoteDetail.withArgKeys(ArgumentKeys.NoteId),
        arguments = listOf(
            navArgument(name = ArgumentKeys.NoteId) {
                type = NavType.LongType
                defaultValue = ArgumentDefaultValues.NewNote
            }
        )
    ) {
        //NoteDetailRoute(navController)
    }
}