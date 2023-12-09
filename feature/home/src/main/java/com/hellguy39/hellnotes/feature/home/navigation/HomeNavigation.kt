package com.hellguy39.hellnotes.feature.home.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.model.OnStartupArguments
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToAboutApp
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSettings
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.HomeRoute
import com.hellguy39.hellnotes.feature.home.util.HomeScreen

fun NavGraphBuilder.homeScreen(
    appState: HellNotesAppState,
    args: OnStartupArguments,
) {
    composable(
        route = Screen.Home.route,
        arguments = listOf(),
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) { from ->
        val context = LocalContext.current

        HomeRoute(
            navigateToSettings = { appState.navigateToSettings(from) },
            navigateToAbout = { appState.navigateToAboutApp(from) },
            navigateToSearch = { appState.navigateToSearch(from) },
            navigateToNoteDetail = { id -> appState.navigateToNoteDetail(from, id) },
            navigateToLabelEdit = { action -> appState.navigateToLabelEdit(from, action) },
            startScreen =
                when (args.action) {
                    stringResource(id = HellNotesStrings.Action.Reminders) -> HomeScreen.Reminders
                    stringResource(id = HellNotesStrings.Action.Archive) -> HomeScreen.Archive
                    stringResource(id = HellNotesStrings.Action.Trash) -> HomeScreen.Trash
                    else -> HomeScreen.NoteList
                },
            onStartupAction = {
                if (args.extraNoteId != ArgumentDefaultValues.EMPTY) {
                    appState.navigateToNoteDetail(from = from, noteId = args.extraNoteId)
                }

                when (args.action) {
                    context.getString(HellNotesStrings.Action.NewNote) -> {
                        appState.navigateToNoteDetail(
                            from = from,
                            noteId = ArgumentDefaultValues.NEW_NOTE,
                        )
                    }
                    else -> Unit
                }
            },
        )
    }
}
