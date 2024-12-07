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
package com.hellguy39.hellnotes.feature.settings.screen.notestyle

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.navigation.Screen
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.defaultNavOptions
import com.hellguy39.hellnotes.core.ui.state.lifecycleIsResumed
import com.hellguy39.hellnotes.feature.settings.SettingsState
import com.hellguy39.hellnotes.feature.settings.screen.settings.SettingsScreen

internal object NoteStyleEditScreen : Screen {
    override val endpoint: String = "note_style_edit"
}

internal fun SettingsState.navigateToNoteStyleEdit(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = NoteStyleEditScreen.endpoint,
            navOptions = navOptions,
        )
    }
}

internal fun NavGraphBuilder.noteStyleEditScreen(settingsState: SettingsState) {
    composable(
        route = NoteStyleEditScreen.endpoint,
        enterTransition = {
            when (initialState.destination.route) {
                SettingsScreen.endpoint -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                SettingsScreen.endpoint -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) {
        NoteStyleEditRoute(
            navigateBack = { settingsState.navigateUp() },
        )
    }
}
