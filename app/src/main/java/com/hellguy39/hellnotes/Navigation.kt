package com.hellguy39.hellnotes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.about_app.util.aboutAppNavigationRoute
import com.hellguy39.hellnotes.about_app.util.aboutAppScreen
import com.hellguy39.hellnotes.note_detail.util.noteDetailNavigationRoute
import com.hellguy39.hellnotes.note_detail.util.noteDetailScreen
import com.hellguy39.hellnotes.notes.util.*
import com.hellguy39.hellnotes.settings.util.settingsNavigationRoute
import com.hellguy39.hellnotes.settings.util.settingsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = noteListNavigationRoute
    ) {
        noteListScreen(navController, Navigations(navController))

        noteDetailScreen(navController)

        settingsScreen(navController)

        aboutAppScreen(navController)
    }
}

class Navigations(private val navController: NavController) : INavigations {
    override fun navigateToSettings() {
        navController.navigateToSettings()
    }

    override fun navigateToAboutApp() {
        navController.navigateToAboutApp()
    }

    override fun navigateToNoteDetail(noteId: Int) {
        navController.navigateToNoteDetail(noteId)
    }
}

fun NavController.navigateToNoteDetail(noteId: Int?, navOptions: NavOptions? = null) {
    navigate(
        route = "$noteDetailNavigationRoute/$noteId",
        navOptions = navOptions
    )
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(
        route = settingsNavigationRoute,
        navOptions = navOptions
    )
}

fun NavController.navigateToAboutApp(navOptions: NavOptions? = null) {
    navigate(
        route = aboutAppNavigationRoute,
        navOptions = navOptions
    )
}

fun NavController.navigateToListNote(navOptions: NavOptions? = null) {
    navigate(
        route = noteDetailNavigationRoute,
        navOptions = navOptions
    )
}
