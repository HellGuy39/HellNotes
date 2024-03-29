package com.hellguy39.hellnotes.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.feature.home.archive.ArchiveRoute
import com.hellguy39.hellnotes.feature.home.label.LabelRoute
import com.hellguy39.hellnotes.feature.home.notes.NotesRoute
import com.hellguy39.hellnotes.feature.home.reminders.RemindersRoute
import com.hellguy39.hellnotes.feature.home.trash.TrashRoute

@Composable
fun HomeNavGraph(
    homeState: HomeState,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    labels: SnapshotStateList<Label>,
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
            NotesRoute(
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
            RemindersRoute(
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
            ArchiveRoute(
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
            TrashRoute(
                homeState = homeState,
                navigateToNoteDetail = navigateToNoteDetail,
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
                LabelRoute(
                    homeState = homeState,
                    navigateToSearch = navigateToSearch,
                    navigateToNoteDetail = navigateToNoteDetail,
                )
            }
        }
    }
}
