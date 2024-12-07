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
package com.hellguy39.hellnotes.feature.aboutapp.screen.termsandconditions

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
internal fun TermsAndConditionsRoute(
    navigateBack: () -> Unit,
    termsAndConditionsViewModel: TermsAndConditionsViewModel = hiltViewModel(),
) {
    TrackScreenView(screenName = "TermsAndConditionsScreen")

    BackHandler { navigateBack() }

    val uiState by termsAndConditionsViewModel.uiState.collectAsStateWithLifecycle()

    TermsAndConditionsScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onTryAgain = {},
    )
}
