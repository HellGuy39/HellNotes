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
package com.hellguy39.hellnotes.feature.aboutapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToChangelog
import com.hellguy39.hellnotes.core.ui.navigations.navigateToPrivacyPolicy
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReset
import com.hellguy39.hellnotes.core.ui.navigations.navigateToTermsAndConditions
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.aboutapp.AboutAppRoute

fun NavGraphBuilder.aboutAppScreen(appState: AppState) {
    composable(
        route = Screen.AboutApp.route,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        AboutAppRoute(
            navigateBack = { appState.navigateUp() },
            navigateToReset = { appState.navigateToReset(from) },
            navigateToChangelog = { appState.navigateToChangelog(from) },
            navigateToPrivacyPolicy = { appState.navigateToPrivacyPolicy(from) },
            navigateToTermsAndConditions = { appState.navigateToTermsAndConditions(from) },
        )
    }
}
