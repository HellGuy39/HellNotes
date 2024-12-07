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
package com.hellguy39.hellnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.GraphState
import com.hellguy39.hellnotes.core.ui.state.rememberGraphState
import com.hellguy39.hellnotes.feature.aboutapp.navigation.aboutNavGraph
import com.hellguy39.hellnotes.feature.aboutapp.navigation.navigateToAboutAppGraph
import com.hellguy39.hellnotes.feature.home.navigation.homeScreen
import com.hellguy39.hellnotes.feature.labeledit.navigation.labelEditScreen
import com.hellguy39.hellnotes.feature.labelselection.navigation.labelSelectionScreen
import com.hellguy39.hellnotes.feature.lock.LockFullScreenDialog
import com.hellguy39.hellnotes.feature.lock.LockViewModel
import com.hellguy39.hellnotes.feature.notedetail.navigations.noteDetailScreen
import com.hellguy39.hellnotes.feature.onboarding.OnBoardingFullScreenDialog
import com.hellguy39.hellnotes.feature.onboarding.OnBoardingViewModel
import com.hellguy39.hellnotes.feature.reminderedit.navigations.reminderEditScreen
import com.hellguy39.hellnotes.feature.search.navigation.searchScreen
import com.hellguy39.hellnotes.feature.settings.navigation.navigateToSettingsGraph
import com.hellguy39.hellnotes.feature.settings.navigation.settingsNavGraph

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GlobalNavGraph(
    graphState: GraphState = rememberGraphState(),
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
    lockViewModel: LockViewModel = hiltViewModel(),
) {
    val onBoardingState by onBoardingViewModel.onBoardingState.collectAsStateWithLifecycle()

    val isLocked by lockViewModel.isLocked.collectAsStateWithLifecycle()

    OnBoardingFullScreenDialog(
        isShowDialog = onBoardingState.isVisible,
        onFinish = { onBoardingViewModel.finishOnBoarding() },
    )

    LockFullScreenDialog(
        isShowDialog = isLocked,
        lockViewModel = lockViewModel,
    )

    NavHost(
        modifier =
            Modifier
                .semantics { testTagsAsResourceId = true },
        navController = graphState.navController,
        startDestination = Screen.Home.route,
    ) {
        homeScreen(
            graphState = graphState,
            navigateToSettings = graphState::navigateToSettingsGraph,
            navigateToAboutApp = graphState::navigateToAboutAppGraph
        )

        noteDetailScreen(graphState)

        reminderEditScreen(graphState)

        labelSelectionScreen(graphState)

        searchScreen(graphState)

        labelEditScreen(graphState)

        settingsNavGraph(
            exitGraph = graphState::navigateUp
        )

        aboutNavGraph(
            exitGraph = graphState::navigateUp
        )
    }
}
