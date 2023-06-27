package com.hellguy39.hellnotes.feature.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.home.HomeViewModel
import com.hellguy39.hellnotes.feature.home.archive.ArchiveRoute
import com.hellguy39.hellnotes.feature.home.labels.LabelsRoute
import com.hellguy39.hellnotes.feature.home.note_list.NoteListRoute
import com.hellguy39.hellnotes.feature.home.reminders.RemindersRoute
import com.hellguy39.hellnotes.feature.home.trash.TrashRoute

//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun HomeNavGraph(
//    drawerState: DrawerState,
//    globalNavController: NavController,
//    navHostController: NavHostController,
//    homeViewModel: HomeViewModel = hiltViewModel()
//) {
//    AnimatedNavHost(
//        navController = navHostController,
//        startDestination = Screen.NoteList.route
//    ) {
//
//        composable(
//            route = Screen.NoteList.route,
//        ) {
//            NoteListRoute(
//                drawerState = drawerState,
//                navController = globalNavController,
//                homeViewModel = homeViewModel
//            )
//        }
//
//        composable(
//            route = Screen.Reminders.route,
//        ) {
//            RemindersRoute(
//                drawerState = drawerState,
//                navController = globalNavController,
//                homeViewModel = homeViewModel
//            )
//        }
//
//        composable(
//            route = Screen.Labels.route,
//        ) {
//            LabelsRoute(
//                drawerState = drawerState,
//                navController = globalNavController,
//                homeViewModel = homeViewModel
//            )
//        }
//
//        composable(
//            route = Screen.Archive.route,
//        ) {
//            ArchiveRoute(
//                drawerState = drawerState,
//                navController = globalNavController,
//                homeViewModel = homeViewModel
//            )
//        }
//
//        composable(
//            route = Screen.Trash.route,
//        ) {
//            TrashRoute(
//                drawerState = drawerState,
//                navController = globalNavController,
//                homeViewModel = homeViewModel
//            )
//        }
//
//    }
//}