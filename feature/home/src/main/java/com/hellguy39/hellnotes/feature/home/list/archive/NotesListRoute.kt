package com.hellguy39.hellnotes.feature.home.list.archive

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.feature.home.MainViewModel
import com.hellguy39.hellnotes.feature.home.list.notes.notesScreen
import com.hellguy39.hellnotes.feature.home.list.reminders.remindersScreen
import com.hellguy39.hellnotes.feature.home.list.trash.trashScreen

@Composable
fun NotesListRoute(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavHost(
        modifier = Modifier,
        navController = mainNavController,
        startDestination = GraphScreen.Main.start().route
    ) {
        notesScreen(mainViewModel)

        remindersScreen(mainViewModel)

        archiveScreen(mainViewModel)

        //labelsScreen(mainViewModel)

        trashScreen(mainViewModel)
    }
}