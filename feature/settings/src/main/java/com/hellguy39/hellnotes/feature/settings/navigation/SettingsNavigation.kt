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

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToBackup
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLanguageSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLockSelection
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteStyleEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteSwipeEdit
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.settings.SettingsRoute

fun NavGraphBuilder.settingsScreen(appState: AppState) {
    composable(
        route = Screen.Settings.route,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        SettingsRoute(
            navigateBack = { appState.navigateUp() },
            navigateToLanguageSelection = { appState.navigateToLanguageSelection(from) },
            navigateToLockSelection = { appState.navigateToLockSelection(from) },
            navigateToNoteStyleEdit = { appState.navigateToNoteStyleEdit(from) },
            navigateToNoteSwipeEdit = { appState.navigateToNoteSwipeEdit(from) },
            navigateToBackup = { appState.navigateToBackup(from) },
        )
    }
}
