package com.hellguy39.hellnotes.navigation.host

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.hellguy39.hellnotes.core.ui.window.calculateContentType
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
import com.hellguy39.hellnotes.feature.settings.SettingsRoute
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel
import com.hellguy39.hellnotes.feature.settings.util.rememberSettingsNavigationItems

@Composable
fun SettingsNavHost(
    displayFeatures: List<DisplayFeature>,
    windowSize: WindowSizeClass,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val windowWidthSize by rememberUpdatedState(windowSize.widthSizeClass)

    val settingsNavController = rememberNavController()

    val contentType = calculateContentType(displayFeatures, windowWidthSize)

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
                onBackNavigation = navigateBack,
                windowWidthSize = windowWidthSize
            )
        }
    }

    Scaffold { innerPadding ->
        navHost(innerPadding)
    }
}