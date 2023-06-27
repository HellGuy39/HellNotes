package com.hellguy39.hellnotes.feature.home.labels

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.feature.home.HomeViewModel

@Composable
fun LabelsRoute(
    navController: NavController,
    drawerState: DrawerState,
    homeViewModel: HomeViewModel,
    labelsViewModel: LabelsViewModel = hiltViewModel()
) {

    val uiState by labelsViewModel.uiState.collectAsStateWithLifecycle()

    LabelsScreen(
        uiState = uiState,
        onLabelClick = { label ->

        },
        onNavigationClick = {

        }
    )

}