package com.hellguy39.hellnotes.navigation.host

import android.app.Activity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.layout.BottomNavigationBarLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationDrawerLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationRailLayout
import com.hellguy39.hellnotes.core.ui.model.HNNavigationType
import com.hellguy39.hellnotes.core.ui.window.calculateContentType
import com.hellguy39.hellnotes.core.ui.window.calculateNavigationType
import com.hellguy39.hellnotes.feature.home.MainRoute
import com.hellguy39.hellnotes.feature.home.MainUiEvent
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.util.rememberHomeBottomNavigationItems
import com.hellguy39.hellnotes.feature.home.util.rememberHomeNavigationItems
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainNavHost(
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit
) {
    val activity = LocalContext.current as Activity
    val displayFeatures = calculateDisplayFeatures(activity)
    val windowSizeClass = calculateWindowSizeClass(activity)

    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigationType = calculateNavigationType(displayFeatures, windowSizeClass.widthSizeClass)
    val contentType = calculateContentType(displayFeatures, windowSizeClass.widthSizeClass)

    val isSearchBarOpen by mainViewModel.isSearchBarOpen.collectAsStateWithLifecycle()
    val openedNoteId by mainViewModel.openedNoteId.collectAsStateWithLifecycle()
    val isDetailOpen by mainViewModel.isDetailOpen.collectAsStateWithLifecycle()

    val onNavigationItemClick: (HNNavigationItemSelection) -> Unit = { item ->
        mainNavController.navigate(item.screen.route) {
            popUpTo(mainNavController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val onDrawerOpen: (Boolean) -> Unit = { isOpen ->
        scope.launch {
            if(isOpen) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }

    val navItems = rememberHomeNavigationItems(onNavigationItemClick)
    val bottomNavItems = rememberHomeBottomNavigationItems(onNavigationItemClick)

    val navHost = remember {
        movableContentOf {
            MainRoute(
                mainNavController = mainNavController,
                mainViewModel = mainViewModel,
                isDetailOpen = isDetailOpen,
                openedNoteId = openedNoteId,
                contentType = contentType,
                displayFeatures = displayFeatures,
                windowWidthSize = windowSizeClass.widthSizeClass,
                onDrawerOpen = onDrawerOpen
            )
        }
    }

    when(navigationType) {
        is HNNavigationType.BottomNavigation -> {
            BottomNavigationBarLayout(
                navItems = navItems,
                bottomNavItems = bottomNavItems,
                currentDestination = currentDestination,
                isVisible = isBottomNavigationBarVisible(bottomNavItems, currentDestination, isDetailOpen, isSearchBarOpen),
                content = { navHost() },
                onNewNoteFabClick = { mainViewModel.onEvent(MainUiEvent.CreateNewNoteAndOpenEditing) },
                drawerState = drawerState,
                onDrawerOpen = onDrawerOpen,
                onSettingsClick = navigateToSettings,
                onAboutClick = navigateToAbout
            )
        }

        is HNNavigationType.NavigationRail -> {
            NavigationRailLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost() },
                onNewNoteFabClick = { mainViewModel.onEvent(MainUiEvent.CreateNewNoteAndOpenEditing) },
                drawerState = drawerState,
                onDrawerOpen = onDrawerOpen,
                onSettingsClick = navigateToSettings,
                onAboutClick = navigateToAbout
            )
        }

        is HNNavigationType.PermanentNavigationDrawer -> {
            NavigationDrawerLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost() },
                onNewNoteFabClick = { mainViewModel.onEvent(MainUiEvent.CreateNewNoteAndOpenEditing) },
                onCloseMenuButtonClick = {  },
                onSettingsClick = navigateToSettings,
                onAboutClick = navigateToAbout
            )
        }
    }
}

fun isBottomNavigationBarVisible(
    bottomNavItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    isDetailOpen: Boolean,
    isSearchBarOpen: Boolean,
): Boolean {

    if (isSearchBarOpen) {
        return false
    }

    if (isDetailOpen) {
        return false
    }

    val bottomRoutes = bottomNavItems.map { it.screen.route }

    return bottomRoutes.contains(currentDestination?.route)
}