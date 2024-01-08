package com.hellguy39.hellnotes.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hellguy39.hellnotes.core.ui.components.HNNavigationDrawer
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.components.rememberDrawerItems
import com.hellguy39.hellnotes.feature.home.notes.components.DrawerSheetContent
import com.hellguy39.hellnotes.feature.home.util.DrawerItem

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToNoteDetail: (id: Long?) -> Unit,
    navigateToLabelEdit: (action: String) -> Unit,
) {
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    if (homeUiState.isIdle) {
        return
    }

    val context = LocalContext.current

    val homeState = rememberHomeState()

    val navBackStackEntry by homeState.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val drawerItems =
        rememberDrawerItems(
            onItemClick = { drawerItem ->
                homeState.navigateToNavigationBarRoute(drawerItem.route)
            },
        )

    val labelItems by remember {
        derivedStateOf {
            homeUiState.labels.map { label ->
                DrawerItem(
                    title = UiText.DynamicString(label.name),
                    icon = UiIcon.DrawableResources(AppIcons.Folder),
                    route = Screen.Label(label.id).withArgs(label.id.toString()),
                    badge =
                        label.noteIds.count().let { count ->
                            if (count >= 100) {
                                UiText.DynamicString("99+")
                            } else if (count > 0) {
                                UiText.DynamicString(count.toString())
                            } else {
                                UiText.Empty
                            }
                        },
                    onClick = { drawerItem ->
                        homeState.navigateToNavigationBarRoute(drawerItem.route)
                    },
                )
            }
        }
    }

    HNNavigationDrawer(
        drawerState = homeState.drawerState,
        drawerSheet = {
            DrawerSheetContent(
                currentDestination = currentDestination,
                drawerItems = drawerItems,
                labelItems = labelItems,
                onCreateNewLabelClick = {
                    navigateToLabelEdit(context.getString(AppStrings.Action.Create))
                    homeState.closeDrawer()
                },
                onManageLabelsClick = {
                    navigateToLabelEdit(context.getString(AppStrings.Action.Edit))
                    homeState.closeDrawer()
                },
                onSettingsClick = {
                    navigateToSettings()
                    homeState.closeDrawer()
                },
                onAboutClick = {
                    navigateToAbout()
                    homeState.closeDrawer()
                },
            )
        },
        content = {
            HomeNavGraph(
                homeState = homeState,
                navigateToSearch = navigateToSearch,
                navigateToNoteDetail = navigateToNoteDetail,
                labels = homeUiState.labels,
            )
        },
    )
}
