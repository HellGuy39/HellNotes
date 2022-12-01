package com.hellguy39.hellnotes.notes.util

import androidx.navigation.*
import androidx.navigation.compose.composable
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

fun NavGraphBuilder.noteListScreen(
    navController: NavController
) {
    composable(route = noteListNavigationRoute) {
        NoteListRoute(navController)
    }
}

fun NavGraphBuilder.noteDetailScreen(
    navController: NavController
) {
    composable(
        route = "$noteDetailNavigationRoute/{$KEY_NOTE_ID}",
        arguments = listOf(
            navArgument(name = KEY_NOTE_ID) {
                type = NavType.IntType
            }
        )
    ) {
        val noteId = it.arguments?.getInt(KEY_NOTE_ID)

        NoteDetailRoute(
            navController = navController,
            noteId = noteId
        )
    }
}