package com.hellguy39.hellnotes.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.core.ui.WindowInfo
import com.hellguy39.hellnotes.core.ui.components.layout.BottomNavigationBarLayout
import com.hellguy39.hellnotes.core.ui.components.layout.NavigationDrawerLayout
import com.hellguy39.hellnotes.core.ui.components.layout.NavigationRailLayout
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.rememberWindowInfo
import com.hellguy39.hellnotes.feature.home.note_list.NoteListViewModel
import com.hellguy39.hellnotes.feature.home.util.getHomeNavigationItems

//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun HomeNavHost(
//    homeViewModel: HomeViewModel = hiltViewModel()
//) {
//    val localNavController = rememberAnimatedNavController()
//
//    val navBackStackEntry by localNavController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    val navItems = getHomeNavigationItems(
//        onItemClick = { item ->
//            localNavController.navigate(item.screen.route) {
//                popUpTo(localNavController.graph.findStartDestination().id)
//                launchSingleTop = true
//            }
//        }
//    )
//
//    val windowInfo = rememberWindowInfo()
//
//    val navHost = remember {
//        movableContentOf<PaddingValues> { innerPadding ->
//            AnimatedNavHost(
//                modifier = Modifier,
//                navController = localNavController,
//                startDestination = Screen.Startup.route
//            ) {
//                composable(Screen.NoteList.route) {
//
//                    val isExpandedWindowsSize = when (windowInfo.screenWidthInfo) {
//                        is WindowInfo.WindowType.Compact -> false
//                        else -> true
//                    }
//
//                    val noteListViewModel: NoteListViewModel = hiltViewModel()
//
//                    if (isExpandedWindowsSize) {
//
//                    } else {
//
//                    }
//
//                    Row(
//                        modifier = Modifier.padding(innerPadding)
//                    ) {
//
//                    }
//                }
//
//                composable(Screen.Reminders.route) {
//
//                }
//            }
//        }
//    }
//
//    when(windowInfo.screenWidthInfo) {
//        is WindowInfo.WindowType.Compact -> {
//            BottomNavigationBarLayout(
//                navItems = navItems,
//                currentDestination = currentDestination,
//                content = { innerPadding -> navHost(innerPadding) },
//                onNewNoteFabClick = {  }
//            )
//        }
//        is WindowInfo.WindowType.Medium -> {
//            NavigationRailLayout(
//                navItems = navItems,
//                currentDestination = currentDestination,
//                content = { navHost(PaddingValues()) },
//                onNewNoteFabClick = {  }
//            )
//        }
//        is WindowInfo.WindowType.Expanded -> {
//            NavigationDrawerLayout(
//                navItems = navItems,
//                currentDestination = currentDestination,
//                content = { navHost(PaddingValues()) },
//                onNewNoteFabClick = {  }
//            )
//        }
//    }
//}