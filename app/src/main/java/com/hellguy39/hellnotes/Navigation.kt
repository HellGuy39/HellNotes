package com.hellguy39.hellnotes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.notes.util.noteDetailScreen
import com.hellguy39.hellnotes.notes.util.noteListNavigationRoute
import com.hellguy39.hellnotes.notes.util.noteListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {

    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = noteListNavigationRoute
    ) {
        noteListScreen(navController)

        noteDetailScreen(navController)
    }
}