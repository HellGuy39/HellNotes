package com.hellguy39.hellnotes.navigation.host

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.activity.main.mainViewModel
import com.hellguy39.hellnotes.core.ui.layout.BottomNavigationBarLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationDrawerLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationRailLayout
import com.hellguy39.hellnotes.core.ui.model.HNNavigationType
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
import com.hellguy39.hellnotes.core.ui.window.rememberNavigationType
import com.hellguy39.hellnotes.feature.home.MainRoute
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.util.getHomeNavigationItems

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    globalNavController: NavController,
    mainViewModel: MainViewModel = mainViewModel(navController = globalNavController)
) {
    val context = LocalContext.current
    val mainNavController = rememberAnimatedNavController()
    val displayFeatures = calculateDisplayFeatures(activity = context as Activity)

    val navigationType = rememberNavigationType()
    val contentType = rememberContentType()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = getHomeNavigationItems(
        onItemClick = { item ->
            mainNavController.navigate(item.screen.route) {
                popUpTo(mainNavController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            MainRoute(
                innerPadding = innerPadding,
                mainNavController = mainNavController,
                mainViewModel = mainViewModel,
                contentType = contentType,
                displayFeatures = displayFeatures,
                onCloseNoteEdit = mainViewModel::closeNoteEdit
            )
        }
    }

    when(navigationType) {
        is HNNavigationType.BottomNavigation -> {
            BottomNavigationBarLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                isVisible = true,
                content = { _ -> navHost(PaddingValues()) },
                onNewNoteFabClick = mainViewModel::newNote
            )
        }
        is HNNavigationType.NavigationRail -> {
            NavigationRailLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues()) },
                onNewNoteFabClick = mainViewModel::newNote,
                onSettingsClick = mainViewModel::openSettings,
                onAboutClick = mainViewModel::openAbout
            )
        }
        is HNNavigationType.PermanentNavigationDrawer -> {
            NavigationDrawerLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues()) },
                onNewNoteFabClick = mainViewModel::newNote,
                onSettingsClick = mainViewModel::openSettings,
                onAboutClick = mainViewModel::openAbout
            )
        }
    }
}