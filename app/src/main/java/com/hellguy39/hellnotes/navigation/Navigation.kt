package com.hellguy39.hellnotes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.about_app.navigation.aboutAppScreen
import com.hellguy39.hellnotes.feature.home.navigation.homeScreen
import com.hellguy39.hellnotes.feature.labels.navigation.labelsScreen
import com.hellguy39.hellnotes.feature.note_detail.navigations.noteDetailScreen
import com.hellguy39.hellnotes.feature.search.navigation.searchScreen
import com.hellguy39.hellnotes.feature.settings.navigation.settingsScreen
import com.hellguy39.hellnotes.feature.welcome.navigation.welcomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    extraNoteId: Long?,
    action: String?,
    startDestination: String,
) {

    val navController = rememberAnimatedNavController()

    val actionNewNote = stringResource(id = HellNotesStrings.Action.NewNote)
    val actionReminders = stringResource(id = HellNotesStrings.Action.Reminders)

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        welcomeScreen(navController)

        homeScreen(
            navController,
            startFromReminders = action == actionReminders
        )

        noteDetailScreen(navController)

        searchScreen(navController)

        labelsScreen(navController)

        settingsScreen(navController)

        aboutAppScreen(navController)
    }.also {
//        LaunchedEffect(Unit) {
//            if (extraNoteId != null) {
//                navController.navigateToNoteDetail(noteId = extraNoteId)
//            }
//
//            when (action) {
//                actionNewNote -> {
//                    navController.navigateToNoteDetail(NEW_NOTE_ID)
//                }
//                actionReminders -> {
//                    //navController.navigateToReminders()
//                }
//            }
//        }
    }
}