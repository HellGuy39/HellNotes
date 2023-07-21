package com.hellguy39.hellnotes.feature.note_detail.navigations

import androidx.compose.animation.*
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.model.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
import com.hellguy39.hellnotes.core.ui.model.Screen

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