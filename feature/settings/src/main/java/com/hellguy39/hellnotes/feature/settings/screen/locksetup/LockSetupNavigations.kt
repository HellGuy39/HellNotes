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

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.navigation.Screen
import com.hellguy39.hellnotes.core.common.navigation.withArgKeys
import com.hellguy39.hellnotes.core.common.navigation.withArgs
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.animations.slideEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.slideExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.asNavigationArgument
import com.hellguy39.hellnotes.core.ui.navigations.defaultNavOptions
import com.hellguy39.hellnotes.core.ui.state.lifecycleIsResumed
import com.hellguy39.hellnotes.feature.settings.SettingsState
import com.hellguy39.hellnotes.feature.settings.screen.lockselection.LockSelectionScreen
import com.hellguy39.hellnotes.feature.settings.screen.settings.SettingsScreen

internal object LockSetupScreen : Screen {
    override val endpoint: String = "lock_setup"
}

internal fun SettingsState.navigateToLockSetup(
    from: NavBackStackEntry,
    lockType: LockScreenType,
    navOptions: NavOptions = defaultNavOptions(),
) {
    if (from.lifecycleIsResumed()) {
        navController.navigate(
            route = LockSetupScreen.withArgs(lockType.tag),
            navOptions = navOptions,
        )
    }
}

internal fun NavGraphBuilder.lockSetupScreen(settingsState: SettingsState) {
    composable(
        route = LockSetupScreen.withArgKeys(Arguments.Type.key),
        arguments =
            listOf(
                Arguments.Type.asNavigationArgument(),
            ),
        enterTransition = {
            when (initialState.destination.route) {
                LockSelectionScreen.endpoint -> slideEnterTransition()
                else -> fadeEnterTransition()
            }
        },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = {
            when (targetState.destination.route) {
                LockSelectionScreen.endpoint -> slideExitTransition()
                else -> fadeExitTransition()
            }
        },
    ) {
        LockSetupRoute(
            navigateBackToSettings = {
                settingsState.navController
                    .popBackStack(route = SettingsScreen.endpoint, inclusive = false)
            },
            navigateBack = { settingsState.navigateUp() },
        )
    }
}
