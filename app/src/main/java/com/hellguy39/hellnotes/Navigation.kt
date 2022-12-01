package com.hellguy39.hellnotes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hellguy39.hellnotes.notes.util.noteDetailScreen
import com.hellguy39.hellnotes.notes.util.noteListNavigationRoute
import com.hellguy39.hellnotes.notes.util.noteListScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = noteListNavigationRoute
    ) {
        noteListScreen(navController)

        noteDetailScreen(navController)

        //noteEditScreen(navController)
    }
}