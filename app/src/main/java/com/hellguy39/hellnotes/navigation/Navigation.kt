package com.hellguy39.hellnotes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.feature.about_app.navigation.aboutAppNavigationRoute
import com.hellguy39.hellnotes.feature.about_app.navigation.aboutAppScreen
import com.hellguy39.hellnotes.core.ui.navigations.INavigations
import com.hellguy39.hellnotes.feature.note_detail.util.NEW_NOTE_ID
import com.hellguy39.hellnotes.feature.note_detail.util.noteDetailNavigationRoute
import com.hellguy39.hellnotes.feature.note_detail.util.noteDetailScreen
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.navigation.homeNavigationRoute
import com.hellguy39.hellnotes.feature.home.navigation.homeScreen
import com.hellguy39.hellnotes.feature.search.navigation.searchNavigationRoute
import com.hellguy39.hellnotes.feature.search.navigation.searchScreen
import com.hellguy39.hellnotes.feature.settings.navigation.settingsNavigationRoute
import com.hellguy39.hellnotes.feature.settings.navigation.settingsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    extraNoteId: Long?,
    action: String?
) {

    val navController = rememberAnimatedNavController()

    val actionNewNote = stringResource(id = HellNotesStrings.Action.NewNote)
    val actionReminders = stringResource(id = HellNotesStrings.Action.Reminders)

    AnimatedNavHost(
        navController = navController,
        startDestination = homeNavigationRoute
    ) {
        homeScreen(navController, Navigations(navController))

        noteDetailScreen(navController)

        searchScreen(navController, Navigations(navController))

        //remindersScreen(navController)

        settingsScreen(navController)

        aboutAppScreen(navController)
    }.also {
        LaunchedEffect(Unit) {
            if (extraNoteId != null) {
                navController.navigateToNoteDetail(noteId = extraNoteId)
            }

            when (action) {
                actionNewNote -> {
                    navController.navigateToNoteDetail(NEW_NOTE_ID)
                }
                actionReminders -> {
                    //navController.navigateToReminders()
                }
            }
        }
    }
}

class Navigations(private val navController: NavController) : INavigations {
    override fun navigateToSettings() {
        navController.navigateToSettings()
    }

    override fun navigateToAboutApp() {
        navController.navigateToAboutApp()
    }

    override fun navigateToNoteDetail(noteId: Long) {
        navController.navigateToNoteDetail(noteId)
    }

    override fun navigateToSearch() {
        navController.navigateToSearch()
    }

    override fun navigateToReminders() {
        //navController.navigateToReminders()
    }
}

fun NavController.navigateToNoteDetail(noteId: Long?, navOptions: NavOptions? = null) {
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

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(
        route = searchNavigationRoute,
        navOptions = navOptions
    )
}

//fun NavController.navigateToReminders(navOptions: NavOptions? = null) {
//    navigate(
//        route = remindersNavigationRoute,
//        navOptions = navOptions
//    )
//}
