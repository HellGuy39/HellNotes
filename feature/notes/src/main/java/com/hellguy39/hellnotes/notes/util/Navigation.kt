package com.hellguy39.hellnotes.notes.util

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.notes.detail.NoteDetailRoute
import com.hellguy39.hellnotes.notes.list.NoteListRoute

const val noteListNavigationRoute = "note_list_route"
const val noteDetailNavigationRoute = "note_detail_route"

const val ARG_NOTE_ID = "arg_note_id"
const val KEY_NOTE_ID = "noteId"
const val NEW_NOTE_ID = -1

fun NavController.navigateToNoteDetail(
    noteId: Int?,
    navOptions: NavOptions? = null
) {
    navigate(
        route = "$noteDetailNavigationRoute/$noteId",
        navOptions = navOptions
    )
}

fun NavController.navigateToListNote(navOptions: NavOptions? = null) {
    navigate(noteDetailNavigationRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteListScreen(
    navController: NavController
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
        NoteListRoute(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.noteDetailScreen(
    navController: NavController
) {
    composable(
        route = "$noteDetailNavigationRoute/{$KEY_NOTE_ID}",
        arguments = listOf(
            navArgument(name = KEY_NOTE_ID) {
                type = NavType.IntType
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
//        val noteId = it.arguments?.getInt(KEY_NOTE_ID)

        NoteDetailRoute(
            navController = navController,
//            noteId = noteId
        )
    }
}