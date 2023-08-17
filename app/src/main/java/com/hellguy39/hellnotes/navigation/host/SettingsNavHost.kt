package com.hellguy39.hellnotes.navigation.host

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.ui.window.calculateContentType
import com.hellguy39.hellnotes.feature.settings.SettingsRoute
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel
import com.hellguy39.hellnotes.feature.settings.util.rememberSettingsNavigationItems

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SettingsNavHost(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val settingsNavController = rememberNavController()

    val activity = LocalContext.current as Activity
    val displayFeatures = calculateDisplayFeatures(activity)
    val windowSizeClass = calculateWindowSizeClass(activity)

    val contentType = calculateContentType(displayFeatures, windowSizeClass.widthSizeClass)

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
                windowWidthSizeClass = windowSizeClass.widthSizeClass
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        navHost(innerPadding)
    }
}