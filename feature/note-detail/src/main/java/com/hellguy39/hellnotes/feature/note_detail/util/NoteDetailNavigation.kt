package com.hellguy39.hellnotes.feature.note_detail.util

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.feature.note_detail.NoteDetailRoute

const val noteDetailNavigationRoute = "note_detail_route"
const val KEY_NOTE_ID = "noteId"
const val NEW_NOTE_ID: Long = -1

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteDetailScreen(
    navController: NavController
) {
    composable(
        route = "$noteDetailNavigationRoute/{$KEY_NOTE_ID}",
        arguments = listOf(
            navArgument(name = KEY_NOTE_ID) {
                type = NavType.LongType
                defaultValue = NEW_NOTE_ID
            }
        ),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        NoteDetailRoute(navController)
    }
}