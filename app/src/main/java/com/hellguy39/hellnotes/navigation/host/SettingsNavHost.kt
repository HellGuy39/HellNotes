package com.hellguy39.hellnotes.navigation.host

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
import com.hellguy39.hellnotes.feature.settings.SettingsRoute
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel
import com.hellguy39.hellnotes.feature.settings.util.rememberSettingsNavigationItems

@Composable
fun SettingsNavHost(
    globalNavController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val settingsNavController = rememberNavController()
    val displayFeatures = calculateDisplayFeatures(activity = context as Activity)

    val contentType = rememberContentType()

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
                currentDestination = currentDestination
            )
        }
    }

    Scaffold { innerPadding ->
        navHost(innerPadding)
    }
}