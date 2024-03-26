package com.hellguy39.hellnotes.feature.settings.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Graph
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.settings.SettingsRoute2
import com.hellguy39.hellnotes.feature.settings.state.SettingsState
import com.hellguy39.hellnotes.feature.settings.state.navigateToAppearanceSettings
import com.hellguy39.hellnotes.feature.settings.state.navigateToGeneralSettings
import com.hellguy39.hellnotes.feature.settings.state.navigateToSecuritySettings
import com.hellguy39.hellnotes.feature.settings.state.navigateToStorageSettings
import com.hellguy39.hellnotes.feature.settings.state.rememberSettingsState
import com.hellguy39.hellnotes.feature.settings.tabs.appearance.AppearanceSettingsRoute
import com.hellguy39.hellnotes.feature.settings.tabs.general.GeneralSettingsRoute
import com.hellguy39.hellnotes.feature.settings.tabs.security.SecuritySettingsRoute
import com.hellguy39.hellnotes.feature.settings.tabs.storage.StorageSettingsRoute

fun NavGraphBuilder.settingsGraph(appState: AppState) {
    composable(
        route = Graph.Settings.route,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        val settingsState = rememberSettingsState()
        SettingsNavGraph(
            navigateBack = { appState.navigateUp() },
            settingsState = settingsState,
        )
    }
}

@Composable
fun SettingsNavGraph(
    navigateBack: () -> Unit,
    settingsState: SettingsState,
) {
    NavHost(
        navController = settingsState.navController,
        startDestination = Graph.Settings.Main.route,
    ) {
        composable(
            route = Graph.Settings.Main.route,
            arguments = listOf(),
            enterTransition = { fadeEnterTransition() },
            exitTransition = { fadeExitTransition() },
            popEnterTransition = { fadeEnterTransition() },
            popExitTransition = { fadeExitTransition() },
        ) { from ->
            SettingsRoute2(
                navigateBack = { navigateBack() },
                navigateToGeneral = { settingsState.navigateToGeneralSettings(from) },
                navigateToStorage = { settingsState.navigateToStorageSettings(from) },
                navigateToSecurity = { settingsState.navigateToSecuritySettings(from) },
                navigateToAppearance = { settingsState.navigateToAppearanceSettings(from) },
            )
        }
        tabComposable(Graph.Settings.Storage.route) {
            StorageSettingsRoute(
                navigateBack = { settingsState.navigateUp() },
            )
        }
        tabComposable(Graph.Settings.Security.route) {
            SecuritySettingsRoute(
                navigateBack = { settingsState.navigateUp() },
            )
        }
        tabComposable(Graph.Settings.Appearance.route) {
            AppearanceSettingsRoute(
                navigateBack = { settingsState.navigateUp() },
            )
        }
        tabComposable(Graph.Settings.General.route) {
            GeneralSettingsRoute(
                navigateBack = { settingsState.navigateUp() },
            )
        }
    }
}

private fun NavGraphBuilder.tabComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        enterTransition = {
            when (initialState.destination.route) {
                Graph.Settings.Main.route -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                Graph.Settings.Main.route -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) { from ->
        content(from)
    }
}
