package com.hellguy39.hellnotes.feature.settings.screen.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.navigation.Screen
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.state.GraphState
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

internal fun NavGraphBuilder.settingsScreen(exitGraph: () -> Unit, graphState: GraphState) {
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
            navigateToLanguageSelection = { graphState.navigateToLanguageSelection(from) },
            navigateToLockSelection = { graphState.navigateToLockSelection(from) },
            navigateToNoteStyleEdit = { graphState.navigateToNoteStyleEdit(from) },
            navigateToNoteSwipeEdit = { graphState.navigateToNoteSwipeEdit(from) },
            navigateToTheme = { graphState.navigateToTheme(from) },
            navigateToBackup = { graphState.navigateToBackup(from) },
            navigateToColorMode = { graphState.navigateToColorMode(from) }
        )
    }
}
