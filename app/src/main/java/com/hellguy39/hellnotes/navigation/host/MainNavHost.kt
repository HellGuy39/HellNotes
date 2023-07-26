package com.hellguy39.hellnotes.navigation.host

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.ui.layout.BottomNavigationBarLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationDrawerLayout
import com.hellguy39.hellnotes.core.ui.layout.NavigationRailLayout
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.model.HNNavigationType
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
import com.hellguy39.hellnotes.core.ui.window.rememberNavigationType
import com.hellguy39.hellnotes.feature.home.MainRoute
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.util.rememberHomeNavigationItems

@Composable
fun MainNavHost(
    globalNavController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val mainNavController = rememberNavController()
    val displayFeatures = calculateDisplayFeatures(activity = context as Activity)

    var navigationType = rememberNavigationType()
    val contentType = rememberContentType()

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
        movableContentOf<PaddingValues> { innerPadding ->
            MainRoute(
                innerPadding = innerPadding,
                mainNavController = mainNavController,
                mainViewModel = mainViewModel,
                isDetailOpen = isDetailOpen,
                openedNoteId = openedNoteId,
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
                isVisible = !isDetailOpen,
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
                onSettingsClick = { globalNavController.navigate(GraphScreen.Global.Settings.route) },
                onAboutClick = { globalNavController.navigate(GraphScreen.Global.About.route) }
            )
        }
        is HNNavigationType.PermanentNavigationDrawer -> {
            NavigationDrawerLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues()) },
                onNewNoteFabClick = mainViewModel::newNote,
                onCloseMenuButtonClick = {  },
                onSettingsClick = { globalNavController.navigate(GraphScreen.Global.Settings.route) },
                onAboutClick = { globalNavController.navigate(GraphScreen.Global.About.route) }
            )
        }
    }
}