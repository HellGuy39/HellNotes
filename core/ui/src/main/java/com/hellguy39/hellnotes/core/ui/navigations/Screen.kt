package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed class Screen(val route: String) {
    object Lock : Screen(route = "lock_screen")
    object Home : Screen(route = "home_screen")
    object Settings : Screen(route = "settings_screen")
    object NoteDetail : Screen(route = "note_detail_screen")
    object Search : Screen(route = "search_screen")
    object AboutApp : Screen(route = "about_app_screen")
    object Labels : Screen(route = "labels_screen")
}

object ArgumentKeys {
    const val NoteId = "noteId"
}

object ArgumentDefaultValues {
    const val NewNote: Long = -1
}

fun NavController.navigateToNoteDetail(
    noteId: Long? = ArgumentDefaultValues.NewNote,
    navOptions: NavOptions? = null
) {
    val noteIdArg = noteId ?: ArgumentDefaultValues.NewNote

    navigate(
        route = "${Screen.NoteDetail.route}/$noteIdArg",
        navOptions = navOptions
    )
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Settings.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToAboutApp(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.AboutApp.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Home.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Search.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLabels(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Labels.route,
        navOptions = navOptions
    )
}

fun NavController.navigateToLock(navOptions: NavOptions? = null) {
    navigate(
        route = Screen.Lock.route,
        navOptions = navOptions
    )
}