package com.hellguy39.hellnotes.navigation.host

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.activity.main.settingsViewModel
import com.hellguy39.hellnotes.core.ui.window.rememberContentType
import com.hellguy39.hellnotes.feature.settings.SettingsRoute
import com.hellguy39.hellnotes.feature.settings.SettingsViewModel
import com.hellguy39.hellnotes.feature.settings.util.getSettingsNavigationItems

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SettingsNavHost(
    globalNavController: NavController,
    settingsViewModel: SettingsViewModel = settingsViewModel(navController = globalNavController)
) {
    val context = LocalContext.current
    val settingsNavController = rememberAnimatedNavController()

    val contentType = rememberContentType()

    val navBackStackEntry by settingsNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val displayFeatures = calculateDisplayFeatures(activity = context as Activity)

    val navItems = getSettingsNavigationItems(
        onItemClick = { item ->
            settingsNavController.navigate(item.screen.route) {
                popUpTo(settingsNavController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )

    SettingsRoute(
        settingsViewModel = settingsViewModel,
        settingsNavController = settingsNavController,
        navItems = navItems,
        contentType = contentType,
        displayFeatures = displayFeatures,
        currentDestination = currentDestination
    )
}