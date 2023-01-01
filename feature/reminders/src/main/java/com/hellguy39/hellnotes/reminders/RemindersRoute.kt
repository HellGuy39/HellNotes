package com.hellguy39.hellnotes.reminders

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersRoute(
    navController: NavController,
    remindersViewModel: RemindersViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val uiState by remindersViewModel.uiState.collectAsState()

    RemindersScreen(
        onNavigationButtonClick = {
            navController.popBackStack()
        },
        scrollBehavior = scrollBehavior,
        uiState = uiState
    )
}