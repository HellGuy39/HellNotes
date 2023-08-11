package com.hellguy39.hellnotes.navigation.host

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
import com.hellguy39.hellnotes.feature.settings.SettingsRoute
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel
import com.hellguy39.hellnotes.feature.settings.util.rememberSettingsNavigationItems

@Composable
fun SettingsNavHost(
    displayFeatures: List<DisplayFeature>,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val settingsNavController = rememberNavController()

    val contentType = rememberContentType(displayFeatures)

    val navBackStackEntry by settingsNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = rememberSettingsNavigationItems(
        onItemClick = { item ->
            settingsNavController.navigate(item.screen.route) {
                popUpTo(settingsNavController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            SettingsRoute(
                settingsViewModel = settingsViewModel,
                settingsNavController = settingsNavController,
                navItems = navItems,
                contentType = contentType,
                displayFeatures = displayFeatures,
                currentDestination = currentDestination,
                onBackNavigation = navigateBack
            )
        }
    }

    Scaffold { innerPadding ->
        navHost(innerPadding)
    }
}