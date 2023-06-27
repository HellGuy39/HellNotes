package com.hellguy39.hellnotes.feature.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.home.note_list.NoteListRoute

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainRoute(
    innerPadding: PaddingValues,
    mainNavController: NavHostController,
    mainViewModel: MainViewModel
) {
    AnimatedNavHost(
        modifier = Modifier,
        navController = mainNavController,
        startDestination = Screen.NoteList.route
    ) {
        composable(Screen.NoteList.route) {
            NoteListRoute()
        }

        composable(Screen.Reminders.route) {

        }

        composable(Screen.Labels.route) {

        }

        composable(Screen.Trash.route) {

        }

        composable(Screen.Archive.route) {

        }
    }
}