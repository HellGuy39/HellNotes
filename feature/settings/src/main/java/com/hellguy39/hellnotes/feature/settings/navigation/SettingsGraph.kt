/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.settings.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.navigation.Graph
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.defaultNavOptions
import com.hellguy39.hellnotes.core.ui.navigations.navigateTo
import com.hellguy39.hellnotes.core.ui.state.GraphState
import com.hellguy39.hellnotes.core.ui.state.rememberGraphState
import com.hellguy39.hellnotes.feature.settings.screen.backup.backupScreen
import com.hellguy39.hellnotes.feature.settings.screen.colormode.colorModeScreen
import com.hellguy39.hellnotes.feature.settings.screen.language.languageSelectionScreen
import com.hellguy39.hellnotes.feature.settings.screen.lockselection.lockSelectionScreen
import com.hellguy39.hellnotes.feature.settings.screen.locksetup.lockSetupScreen
import com.hellguy39.hellnotes.feature.settings.screen.notestyle.noteStyleEditScreen
import com.hellguy39.hellnotes.feature.settings.screen.noteswipe.noteSwipeEditScreen
import com.hellguy39.hellnotes.feature.settings.screen.settings.SettingsScreen
import com.hellguy39.hellnotes.feature.settings.screen.settings.settingsScreen
import com.hellguy39.hellnotes.feature.settings.screen.theme.themeScreen

object SettingsNavGraph : Graph {
    override val endpoint: String = "settings"
}

fun GraphState.navigateToSettingsGraph(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) = navigateTo(from, SettingsNavGraph.endpoint, navOptions)

@OptIn(ExperimentalComposeUiApi::class)
fun NavGraphBuilder.settingsNavGraph(exitGraph: () -> Unit) {
    composable(
        route = SettingsNavGraph.endpoint,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        val graphState = rememberGraphState()

        NavHost(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            navController = graphState.navController,
            startDestination = SettingsScreen.endpoint,
        ) {
            settingsScreen(
                exitGraph = exitGraph,
                graphState = graphState
            )

            backupScreen(graphState)

            lockSelectionScreen(graphState)

            lockSetupScreen(graphState)

            languageSelectionScreen(graphState)

            noteStyleEditScreen(graphState)

            noteSwipeEditScreen(graphState)

            themeScreen(graphState)

            colorModeScreen(graphState)
        }
    }
}
