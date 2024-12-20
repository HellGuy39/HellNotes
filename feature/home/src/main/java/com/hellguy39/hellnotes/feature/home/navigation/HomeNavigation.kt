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
package com.hellguy39.hellnotes.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToAboutApp
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLabelEdit
import com.hellguy39.hellnotes.core.ui.navigations.navigateToNoteDetail
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSearch
import com.hellguy39.hellnotes.core.ui.navigations.navigateToSettings
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.home.HomeRoute

fun NavGraphBuilder.homeScreen(
    appState: AppState,
) {
    composable(
        route = Screen.Home.route,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        HomeRoute(
            navigateToSettings = { appState.navigateToSettings(from) },
            navigateToAbout = { appState.navigateToAboutApp(from) },
            navigateToSearch = { appState.navigateToSearch(from) },
            navigateToNoteDetail = { id -> appState.navigateToNoteDetail(from, id) },
            navigateToLabelEdit = { action -> appState.navigateToLabelEdit(from, action) },
        )
    }
}
