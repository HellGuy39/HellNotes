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

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.feature.aboutapp.components.AboutAppScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    onNavigationButtonClick: () -> Unit = {},
    selection: AboutAppScreenSelection = AboutAppScreenSelection(),
) {
    BackHandler { onNavigationButtonClick() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier.fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            AboutAppScreenContent(
                innerPadding = innerPadding,
                selection = selection,
            )
        },
        topBar = {
            HNLargeTopAppBar(
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = AppStrings.AppName),
                scrollBehavior = scrollBehavior,
            )
        },
    )
}

data class AboutAppScreenSelection(
    val onGithub: () -> Unit = {},
    val onChangelog: () -> Unit = {},
    val onPrivacyPolicy: () -> Unit = {},
    val onTermsAndConditions: () -> Unit = {},
    val onProvideFeedback: () -> Unit = {},
    val onReset: () -> Unit = {},
)

@Preview(showBackground = true)
@Composable
fun AboutAppScreenPreview() {
    HellNotesTheme {
        AboutAppScreen()
    }
}
