package com.hellguy39.hellnotes.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.feature.home.archive.ArchiveScreen
import com.hellguy39.hellnotes.feature.home.label.LabelScreen
import com.hellguy39.hellnotes.feature.home.notes.NotesScreen
import com.hellguy39.hellnotes.feature.home.reminders.RemindersScreen
import com.hellguy39.hellnotes.feature.home.trash.TrashScreen

@Composable
fun HomeNavGraph(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    labels: List<Label>,
) {
    NavHost(
        navController = homeState.navController,
        startDestination = Screen.Notes.route,
    ) {
        composable(
            route = Screen.Notes.route,
            arguments = listOf(),
            deepLinks = listOf(),
            enterTransition = { fadeEnterTransition() },
            exitTransition = { fadeExitTransition() },
            popEnterTransition = { fadeEnterTransition() },
            popExitTransition = { fadeExitTransition() },
        ) {
            NotesScreen(
                homeState = homeState,
                navigateToSearch = navigateToSearch,
                navigateToNoteDetail = navigateToNoteDetail,
            )
        }
        composable(
            route = Screen.Reminders.route,
            arguments = listOf(),
            deepLinks = listOf(),
            enterTransition = { fadeEnterTransition() },
            exitTransition = { fadeExitTransition() },
            popEnterTransition = { fadeEnterTransition() },
            popExitTransition = { fadeExitTransition() },
        ) {
            RemindersScreen(
                homeState = homeState,
                navigateToSearch = navigateToSearch,
                navigateToNoteDetail = navigateToNoteDetail,
            )
        }

        composable(
            route = Screen.Archive.route,
            arguments = listOf(),
            deepLinks = listOf(),
            enterTransition = { fadeEnterTransition() },
            exitTransition = { fadeExitTransition() },
            popEnterTransition = { fadeEnterTransition() },
            popExitTransition = { fadeExitTransition() },
        ) {
            ArchiveScreen(
                homeState = homeState,
                navigateToSearch = navigateToSearch,
                navigateToNoteDetail = navigateToNoteDetail,
            )
        }
        composable(
            route = Screen.Trash.route,
            arguments = listOf(),
            deepLinks = listOf(),
            enterTransition = { fadeEnterTransition() },
            exitTransition = { fadeExitTransition() },
            popEnterTransition = { fadeEnterTransition() },
            popExitTransition = { fadeExitTransition() },
        ) {
            TrashScreen(
                homeState = homeState,
            )
        }
        labels.forEach { label ->
            composable(
                route = Screen.Label(label.id).withArgKeys(Arguments.LabelId),
                arguments =
                    listOf(
                        Arguments.LabelId.asNavigationArgument(),
                    ),
                deepLinks = listOf(),
                enterTransition = { fadeEnterTransition() },
                exitTransition = { fadeExitTransition() },
                popEnterTransition = { fadeEnterTransition() },
                popExitTransition = { fadeExitTransition() },
            ) {
                LabelScreen(
                    homeState = homeState,
                    navigateToSearch = navigateToSearch,
                    navigateToNoteDetail = navigateToNoteDetail,
                )
            }
        }
    }
}
