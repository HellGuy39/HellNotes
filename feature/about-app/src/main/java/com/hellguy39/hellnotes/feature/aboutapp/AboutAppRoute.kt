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
package com.hellguy39.hellnotes.feature.aboutapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.feature.aboutapp.util.openGithub
import com.hellguy39.hellnotes.feature.aboutapp.util.provideFeedback

@Composable
fun AboutAppRoute(
    navigateBack: () -> Unit,
    navigateToReset: () -> Unit,
    navigateToChangelog: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToTermsAndConditions: () -> Unit,
) {
    TrackScreenView(screenName = "AboutScreen")

    val context = LocalContext.current

    AboutAppScreen(
        onNavigationButtonClick = { navigateBack() },
        selection =
            AboutAppScreenSelection(
                onReset = navigateToReset,
                onChangelog = navigateToChangelog,
                onGithub = { context.openGithub() },
                onPrivacyPolicy = navigateToPrivacyPolicy,
                onProvideFeedback = { context.provideFeedback() },
                onTermsAndConditions = { navigateToTermsAndConditions() },
            ),
    )
}
