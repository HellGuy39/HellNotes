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
package com.hellguy39.hellnotes.feature.settings.screen.lockselection

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
import com.hellguy39.hellnotes.core.ui.state.GraphState
import com.hellguy39.hellnotes.core.ui.state.lifecycleIsResumed
import com.hellguy39.hellnotes.feature.settings.screen.locksetup.navigateToLockSetup
import com.hellguy39.hellnotes.feature.settings.screen.settings.SettingsScreen

internal object LockSelectionScreen : Screen {
    override val endpoint: String = "lock_selection"
}

internal fun GraphState.navigateToLockSelection(
    from: NavBackStackEntry,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = LockSelectionScreen.endpoint,
            navOptions = navOptions,
        )
    }
}

internal fun NavGraphBuilder.lockSelectionScreen(graphState: GraphState) {
    composable(
        route = LockSelectionScreen.endpoint,
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
    ) { from ->
        LockSelectionRoute(
            navigateBack = graphState::navigateUp,
            navigateToLockSetup = { lockType ->
                graphState.navigateToLockSetup(from, lockType)
            },
        )
    }
}
