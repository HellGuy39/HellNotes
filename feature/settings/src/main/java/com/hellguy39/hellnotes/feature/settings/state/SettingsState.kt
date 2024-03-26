package com.hellguy39.hellnotes.feature.settings.state

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hellguy39.hellnotes.core.ui.navigations.Graph
import com.hellguy39.hellnotes.core.ui.state.lifecycleIsResumed
import com.hellguy39.hellnotes.core.ui.state.resources
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberSettingsState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(navController, resources, coroutineScope) {
    SettingsState(navController, resources, coroutineScope)
}

@Stable
class SettingsState(
    val navController: NavHostController,
    private val resources: Resources,
    coroutineScope: CoroutineScope,
) {
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateUp() {
        navController.navigateUp()
    }
}

fun SettingsState.navigateToStorageSettings(
    from: NavBackStackEntry,
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Graph.Settings.Storage.route,
        )
    }
}

fun SettingsState.navigateToGeneralSettings(
    from: NavBackStackEntry,
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Graph.Settings.General.route,
        )
    }
}

fun SettingsState.navigateToSecuritySettings(
    from: NavBackStackEntry,
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Graph.Settings.Security.route,
        )
    }
}

fun SettingsState.navigateToAppearanceSettings(
    from: NavBackStackEntry,
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = Graph.Settings.Appearance.route,
        )
    }
}
