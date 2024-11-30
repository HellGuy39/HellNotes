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
package com.hellguy39.hellnotes.feature.settings.screen.locksetup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.core.ui.state.AppState

fun NavGraphBuilder.lockSetupScreen(appState: AppState) {
    composable(
        route = Screen.LockSetup.withArgKeys(Arguments.Type.key),
        arguments =
            listOf(
                Arguments.Type.asNavigationArgument(),
            ),
        enterTransition = {
            when (initialState.destination.route) {
                Screen.LockSelection.route -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.LockSelection.route -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) {
        com.hellguy39.hellnotes.feature.settings.screen.locksetup.LockSetupRoute(
            navigateBackToSettings = {
                appState.navController
                    .popBackStack(route = Screen.Settings.route, inclusive = false)
            },
            navigateBack = { appState.navigateUp() },
        )
    }
}
