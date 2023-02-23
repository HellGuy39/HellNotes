package com.hellguy39.hellnotes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.activity.main.MainActivity
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.about_app.navigation.aboutAppScreen
import com.hellguy39.hellnotes.feature.home.navigation.homeScreen
import com.hellguy39.hellnotes.feature.home.util.HomeScreen
import com.hellguy39.hellnotes.feature.label_edit.navigation.labelEditScreen
import com.hellguy39.hellnotes.feature.label_selection.navigation.labelSelectionScreen
import com.hellguy39.hellnotes.feature.language_selection.navigation.languageSelectionScreen
import com.hellguy39.hellnotes.feature.lock_selection.navigation.lockSelectionScreen
import com.hellguy39.hellnotes.feature.lock_setup.navigation.lockSetupScreen
import com.hellguy39.hellnotes.feature.note_detail.navigations.noteDetailScreen
import com.hellguy39.hellnotes.feature.reminder_edit.navigations.reminderEditScreen
import com.hellguy39.hellnotes.feature.search.navigation.searchScreen
import com.hellguy39.hellnotes.feature.settings.navigation.settingsScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
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
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            },
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

        reminderEditScreen(navController)

        labelSelectionScreen(navController)

        searchScreen(navController)

        labelEditScreen(navController)

        settingsScreen(navController)

        lockSelectionScreen(navController)

        lockSetupScreen(navController)

        languageSelectionScreen(navController)

        aboutAppScreen(navController)
    }.also {
        LaunchedEffect(key1 = isStartUpActionPassed) {
            if (!isStartUpActionPassed) {

                onStartUpActionPassed()

                if (extraNoteId != null && extraNoteId != MainActivity.EMPTY_ARG) {
                    navController.navigateToNoteDetail(noteId = extraNoteId)
                }

                when (action) {
                    actionNewNote -> {
                        navController.navigateToNoteDetail(ArgumentDefaultValues.NewNote)
                    }
                    else -> Unit
                }
            }
        }
    }
}