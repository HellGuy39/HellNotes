package com.hellguy39.hellnotes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.about_app.navigation.aboutAppScreen
import com.hellguy39.hellnotes.feature.home.navigation.homeScreen
import com.hellguy39.hellnotes.feature.home.util.HomeScreen
import com.hellguy39.hellnotes.feature.labels.navigation.labelsScreen
import com.hellguy39.hellnotes.feature.language_selection.navigation.languageSelectionScreen
import com.hellguy39.hellnotes.feature.lock_selection.navigation.lockSelectionScreen
import com.hellguy39.hellnotes.feature.lock_setup.navigation.lockSetupScreen
import com.hellguy39.hellnotes.feature.note_detail.navigations.noteDetailScreen
import com.hellguy39.hellnotes.feature.search.navigation.searchScreen
import com.hellguy39.hellnotes.feature.settings.navigation.settingsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    extraNoteId: Long?,
    action: String?,
    isStartUpActionPassed: Boolean,
    onStartUpActionPassed: () -> Unit
) {
    val navController = rememberAnimatedNavController()

    val actionNewNote = stringResource(id = HellNotesStrings.Action.NewNote)
    val actionReminders = stringResource(id = HellNotesStrings.Action.Reminders)
    val actionTrash = stringResource(id = HellNotesStrings.Action.Trash)
    val actionArchive = stringResource(id = HellNotesStrings.Action.Archive)

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        homeScreen(
            navController,
            startScreen = when(action) {
                actionReminders -> HomeScreen.Reminders
                actionArchive -> HomeScreen.Archive
                actionTrash -> HomeScreen.Trash
                else -> HomeScreen.NoteList
            }
        )

        noteDetailScreen(navController)

        searchScreen(navController)

        labelsScreen(navController)

        settingsScreen(navController)

        lockSelectionScreen(navController)

        lockSetupScreen(navController)

        languageSelectionScreen(navController)

        aboutAppScreen(navController)
    }.also {
        LaunchedEffect(key1 = isStartUpActionPassed) {
            if (!isStartUpActionPassed) {

                onStartUpActionPassed()

                if (extraNoteId != null) {
                    navController.navigateToNoteDetail(noteId = extraNoteId)
                }
                when (action) {
                    actionNewNote -> {
                        navController.navigateToNoteDetail(ArgumentDefaultValues.NewNote)
                    }
                }
            }
        }
    }
}