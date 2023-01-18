package com.hellguy39.hellnotes.feature.note_list.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.INavigations
import com.hellguy39.hellnotes.feature.note_list.NoteListRoute

const val noteListNavigationRoute = "note_list_route"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteListScreen(
    navController: NavController,
    navigations: INavigations
) {
    composable(
        route = noteListNavigationRoute,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -300 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -300 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        }
    ) {
        NoteListRoute(navigations)
    }
}