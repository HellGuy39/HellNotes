package com.hellguy39.hellnotes.feature.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.uri.DeeplinkRoute
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.core.ui.state.HomeState
import com.hellguy39.hellnotes.feature.home.archive.ArchiveScreen
import com.hellguy39.hellnotes.feature.home.label.LabelScreen
import com.hellguy39.hellnotes.feature.home.notelist.NotesScreen
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
            deepLinks =
                listOf(
                    navDeepLink {
                        uriPattern =
                            DeeplinkRoute.fromApp()
                                .addPath(Screen.Reminders.route)
                                .asString()
                    },
                ),
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
            deepLinks =
                listOf(
                    navDeepLink {
                        uriPattern =
                            DeeplinkRoute.fromApp()
                                .addPath(Screen.Archive.route)
                                .asString()
                    },
                ),
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
            deepLinks =
                listOf(
                    navDeepLink {
                        uriPattern =
                            DeeplinkRoute.fromApp()
                                .addPath(Screen.Trash.route)
                                .asString()
                    },
                ),
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
