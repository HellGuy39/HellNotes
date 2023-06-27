package com.hellguy39.hellnotes.navigation.host

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.core.ui.WindowInfo
import com.hellguy39.hellnotes.core.ui.components.layout.BottomNavigationBarLayout
import com.hellguy39.hellnotes.core.ui.components.layout.NavigationDrawerLayout
import com.hellguy39.hellnotes.core.ui.components.layout.NavigationRailLayout
import com.hellguy39.hellnotes.core.ui.rememberWindowInfo
import com.hellguy39.hellnotes.feature.home.MainRoute
import com.hellguy39.hellnotes.feature.home.util.getHomeNavigationItems
import com.hellguy39.hellnotes.navigation.graph.mainViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    globalNavController: NavController,
) {
    val mainNavController = rememberAnimatedNavController()
    val windowInfo = rememberWindowInfo()

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
                mainViewModel = mainViewModel(navController = globalNavController)
            )
        }
    }

    when(windowInfo.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> {
            BottomNavigationBarLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                isVisible = true,
                content = { innerPadding -> navHost(innerPadding) },
                onNewNoteFabClick = {  }
            )
        }
        is WindowInfo.WindowType.Medium -> {
            NavigationRailLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues()) },
                onNewNoteFabClick = {  }
            )
        }
        is WindowInfo.WindowType.Expanded -> {
            NavigationDrawerLayout(
                navItems = navItems,
                currentDestination = currentDestination,
                content = { navHost(PaddingValues()) },
                onNewNoteFabClick = {  }
            )
        }
    }
}