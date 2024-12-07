package com.hellguy39.hellnotes.feature.settings.screen.settings

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.navigation.Screen
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.defaultNavOptions
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.core.ui.state.lifecycleIsResumed
import com.hellguy39.hellnotes.feature.settings.SettingsState
import com.hellguy39.hellnotes.feature.settings.navigation.SettingsNavGraph
import com.hellguy39.hellnotes.feature.settings.screen.backup.navigateToBackup
import com.hellguy39.hellnotes.feature.settings.screen.colormode.navigateToColorMode
import com.hellguy39.hellnotes.feature.settings.screen.language.navigateToLanguageSelection
import com.hellguy39.hellnotes.feature.settings.screen.lockselection.navigateToLockSelection
import com.hellguy39.hellnotes.feature.settings.screen.notestyle.navigateToNoteStyleEdit
import com.hellguy39.hellnotes.feature.settings.screen.noteswipe.navigateToNoteSwipeEdit
import com.hellguy39.hellnotes.feature.settings.screen.theme.navigateToTheme

internal object SettingsScreen : Screen {
    override val endpoint: String = "settings"
}

fun AppState.navigateToSettingsGraph(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = SettingsNavGraph.endpoint,
            navOptions = navOptions,
        )
    }
}

internal fun NavGraphBuilder.settingsScreen(exitGraph: () -> Unit, settingsState: SettingsState) {
    composable(
        route = SettingsScreen.endpoint,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        SettingsRoute(
            navigateBack = exitGraph,
            navigateToLanguageSelection = { settingsState.navigateToLanguageSelection(from) },
            navigateToLockSelection = { settingsState.navigateToLockSelection(from) },
            navigateToNoteStyleEdit = { settingsState.navigateToNoteStyleEdit(from) },
            navigateToNoteSwipeEdit = { settingsState.navigateToNoteSwipeEdit(from) },
            navigateToTheme = { settingsState.navigateToTheme(from) },
            navigateToBackup = { settingsState.navigateToBackup(from) },
            navigateToColorMode = { settingsState.navigateToColorMode(from) }
        )
    }
}
