package com.hellguy39.hellnotes.navigation.host

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.core.ui.layout.BottomNavigationBarLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationDrawerLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationRailLayout
import com.hellguy39.hellnotes.core.ui.model.HNNavigationType
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
import com.hellguy39.hellnotes.core.ui.window.rememberNavigationType
import com.hellguy39.hellnotes.feature.home.MainRoute
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.util.rememberHomeNavigationItems

@Composable
fun MainNavHost(
    displayFeatures: List<DisplayFeature>,
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit
) {
    val mainNavController = rememberNavController()

    var navigationType = rememberNavigationType(displayFeatures)
    val contentType = rememberContentType(displayFeatures)

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isDetailOpen by mainViewModel.isDetailOpen.collectAsStateWithLifecycle()
    val openedNoteId by mainViewModel.openedNoteId.collectAsStateWithLifecycle()

    val navItems = rememberHomeNavigationItems(
        onItemClick = { item ->
            mainNavController.navigate(item.screen.route) {
                popUpTo(mainNavController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

    val navHost = remember {
        movableContentOf<PaddingValues, () -> Unit> { innerPadding, onDrawerOpen ->
            MainRoute(
                innerPadding = innerPadding,
                mainNavController = mainNavController,
                mainViewModel = mainViewModel,
                isDetailOpen = isDetailOpen,
                openedNoteId = openedNoteId,
                contentType = contentType,
                displayFeatures = displayFeatures,
                onCloseNoteEdit = mainViewModel::closeNoteEdit,
                onDrawerOpen = onDrawerOpen
            )
        }
    }

    when(navigationType) {
        is HNNavigationType.BottomNavigation -> {
            BottomNavigationBarLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                isVisible = !isDetailOpen,
                content = { _ , onDrawerOpen -> navHost(PaddingValues(), onDrawerOpen) },
                onNewNoteFabClick = mainViewModel::newNote,
                onSettingsClick = navigateToSettings,
                onAboutClick = navigateToAbout
            )
        }
        is HNNavigationType.NavigationRail -> {
            NavigationRailLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues(), {}) },
                onNewNoteFabClick = mainViewModel::newNote,
                onSettingsClick = navigateToSettings,
                onAboutClick = navigateToAbout
            )
        }
        is HNNavigationType.PermanentNavigationDrawer -> {
            NavigationDrawerLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues(), {}) },
                onNewNoteFabClick = mainViewModel::newNote,
                onCloseMenuButtonClick = {  },
                onSettingsClick = navigateToSettings,
                onAboutClick = navigateToAbout
            )
        }
    }
}